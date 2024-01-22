import jsInterfaceAsyncMethodCallbackUtils from '@/utils/androidJsInterfaces/asyncSupport/callback'
import * as uuid from 'uuid'

const jsInterfaceAsyncSupportUtils = {
  getAsyncMethodStub: (jsInterfaceName, methodName) => {
    return (...args) => new Promise(async (resolve, reject) => {
      let callbackId = uuid.v4()
      console.log(callbackId)
      jsInterfaceAsyncMethodCallbackUtils.addCallback(callbackId, resolve, reject)
      //noinspection JSUnresolvedReference
      window['android_AsyncTaskJsInterface'].invokeAsyncMethod(jsInterfaceName, methodName, callbackId, JSON.stringify(args))
    })
  }
}

export default jsInterfaceAsyncSupportUtils