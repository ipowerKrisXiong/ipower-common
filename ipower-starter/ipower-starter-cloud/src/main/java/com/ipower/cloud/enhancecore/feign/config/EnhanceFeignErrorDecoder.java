package com.ipower.cloud.enhancecore.feign.config;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * @Description
 * 当使用feign的时候，http状态不等于2xx的时候进入这个异常解码器，注意看父类接口ErrorDecoder.decode方法里面
 * 根据http状态码，把400-499，500-599 分为client异常和server异常，然后都包装未FeignException抛出，FeignException会进入hystrix断路器计数，不需要参与计数的要自己包装为HystrixBadRequestException再抛出
 * 最终抛出的异常会在HystrixInvocationHandler.invoke  new HystrixCommand.run方法里面catch (Exception e)
 * 断路器的计数接口和类请查看：
 * HystrixCircuitBreaker 断路器
 * HystrixCommandMetrics 断路器计数  里面有个内部类HealthCounts.plus  healthCountsStream
 * 其Hystrix滑动时间窗口核心实现为（看看就好，rxjava不用会）
 *
 * 提供了HealthCountsStream提供实时健康检查数据，其中里面有个对象HealthCounts记录滑动窗口期间请求数(总数、失败数、失败百分比)
 * 有滑动时间窗口，肯定也有持续累积窗口BucketedCumulativeCounterStream
 * https://blog.csdn.net/qq_28666081/article/details/120518193
 * https://zhuanlan.zhihu.com/p/409109888
 * https://cloud.tencent.com/developer/article/1600699
 *
 * 断路计数入口方法
 * 所有的HystrixCommond执行，最终在 AbstractCommand  executeCommandAndObserve
 *         return execution.doOnNext(markEmits)
 *                 .doOnCompleted(markOnCompleted)
 *                 .onErrorResumeNext(handleFallback)
 *                 .doOnEach(setRequestContext);
 *
 *          断路计数我们只需关心   onErrorResumeNext(handleFallback)这个方法，
 *          它的触发条件是：发射数据时（目标方法执行时）出现异常便会回调此函数，因此需要看看handleFallback的逻辑。
 *          正常执行（成功）时不会回调此函数，而是回调的doOnCompleted(markOnCompleted)
 *          主要看上面这个传入的参数handleFallback
 *          里面有写
 *          // ==============针对不同异常的处理==============
 *         if (e instanceof RejectedExecutionException) {
 *             return handleThreadPoolRejectionViaFallback(e);
 *         } else if (t instanceof HystrixTimeoutException) {
 *             return handleTimeoutViaFallback();
 *         } else if (t instanceof HystrixBadRequestException) {
 *             return handleBadRequestByEmittingError(e);
 *         } else {
 *             return handleFailureViaFallback(e);
 *         }
 *         注意看handleBadRequestByEmittingError(e)中逻辑
 *         这就是HystrixBadRequestException的特殊对待逻辑，它发出的事件类型是HystrixEventType.BAD_REQUEST，而此事件类型是不会被HealthCounts作为健康指标所统计的，因此它并不会触发熔断器。
 *         这里完成滑动窗口计数后，继续执行handleFailureViaFallback，然后走到真正的fallback降级逻辑里面执行
 *
 *
 * 用自己定义的http非200-300异常解码处理器替代原有的ErrorDecoder里面的default处理器
 * 把其中的4xx状态包装为HystrixBadRequestException异常，这样就抛出就可以不计入熔断器统计了
 * @Author xl
 * @Date 2019/10/16
 */
public class EnhanceFeignErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {

        int httpStatus = response.status();
        //处理异常为4xx的时候，不进入熔断计数,抛出HystrixBadRequestException异常
        //异常处理源码在AbstractCommand#executeCommandAndObserve --> handleFallback
        //开启404decode的时候不会走这里，404就会直接用404decode返回一个空值回去，类似于fallback，代码位置feign.SynchronousMethodHandler.executeAndDecode
        if(httpStatus==404
                ||httpStatus==400 //无效请求,说明服务器无法理解用户的请求，除非进行修改
                ||httpStatus==405 //资源被禁止，有可能是文件目录权限不够导致的
                ||httpStatus==403 //出现403是因为服务器拒绝了你的地址请求，很有可能是你根本就没权限访问网站
                ||httpStatus==409 // Web 服务器认为，由于与一些已经确立的规则相冲突， 客户端（如您的浏览器或我们的 CheckUpDown 机器人）提交的请求无法完成
                ||(httpStatus>=400&&httpStatus<500)//4xx状态全部不能进入熔断，这里需要做处理
                ){

            return new HystrixBadRequestException("4XX HTTP STATUS DECODE!" + response.toString());

        }
        //http状态为3xx的时候会自动重定向到不了这里
        //如果是5xx或其它非>=200&<300状态则启用默认解码器的方式构建FeignException或者RetryableException
        return super.decode(methodKey,response);

    }

}
