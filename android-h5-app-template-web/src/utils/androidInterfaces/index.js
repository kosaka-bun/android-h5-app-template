import companion from '@/utils/androidInterfaces/companion'
import codeUtils from '@/utils/code'

//noinspection JSUnusedLocalSymbols
const androidInterfaces = {
  basicJsInterface: companion.getAndroidInterfaceStub('BasicJsInterface', {
    openNewWebActivity: path => {
      companion.androidInterfaceWarning()
      window.location.href = path
    },
    getUuid: () => codeUtils.randomUUID(),
    test: companion.emptyImplementation(),
    asyncMethodTest: {
      isAsync: true,
      fallback: (a, b) => {
        companion.androidInterfaceWarning()
        return {
          sum: a + b
        }
      }
    }
  }),
  asyncTaskJsInterface: companion.getAndroidInterfaceStub('AsyncTaskJsInterface', {
    invokeAsyncMethod: (jsInterfaceName, methodName, callbackId, args) => {
      companion.androidInterfaceWarning()
    },
    getAsyncMethodResult: id => {
      companion.androidInterfaceWarning()
      return ''
    }
  })
}

export const androidAsyncMethodCallbacks = {}

//noinspection JSUnusedGlobalSymbols
export const androidAsyncMethodCallbackUtils = {
  //仅main.js中调用一次
  exposeToGlobal() {
    window.androidAsyncMethodCallbackUtils = this
  },
  invokeCallback(id) {
    let callback = androidAsyncMethodCallbacks[id]
    if(callback == null) return
    callback()
  },
  addCallback(id, resolve, reject) {
    androidAsyncMethodCallbacks[id] = () => {
      this.removeCallback(id)
      let resultObj = JSON.parse(androidInterfaces.asyncTaskJsInterface.getAsyncMethodResult(id)) ?? {
        isResolve: false,
        isPlainText: true,
        result: ''
      }
      if(resultObj.isResolve === true) {
        resolve(resultObj.isPlainText ? resultObj.result : JSON.parse(resultObj.result))
        return
      }
      reject(resultObj.isPlainText ? resultObj.result : JSON.parse(resultObj.result))
    }
  },
  removeCallback(id) {
    delete androidAsyncMethodCallbacks[id]
  }
}

export default androidInterfaces