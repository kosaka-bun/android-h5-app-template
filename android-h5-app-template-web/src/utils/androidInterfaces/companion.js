import androidInterfaces, { androidAsyncMethodCallbackUtils } from '@/utils/androidInterfaces/index'

const androidInterfacesCompanion = {
  emptyImplementation: () => () => this.androidInterfaceWarning(),
  androidInterfaceWarning() {
    console.warn('You are calling a Android JavaScript Interface function directly in browser!')
  },
  getAndroidInterfaceStub(jsInterfaceName, methodsDefinitions) {
    let jsInterface = window[`android_${jsInterfaceName}`]
    let stub = {}
    Object.keys(methodsDefinitions).forEach(it => {
      let definition = methodsDefinitions[it]
      if(definition instanceof Function) {
        stub[it] = jsInterface != null ? this.getWrappedInterfaceMethod(jsInterface, it) : definition
        return
      }
      if(definition instanceof Object) {
        let isAsync = definition['isAsync'] ?? false
        if(!isAsync) {
          stub[it] = jsInterface != null ? this.getWrappedInterfaceMethod(jsInterface, it) : definition.fallback
        } else {
          stub[it] = jsInterface != null ? this.getAndroidAsyncMethodStub(jsInterfaceName, it) : (
              async () => definition.fallback()
          )
        }
        return
      }
      throw new Error(`Unknown Android interface stub definition: ${it} -> ${typeof definition}`)
    })
    return stub
  },
  getWrappedInterfaceMethod(jsInterface, methodName) {
    return (...args) => jsInterface[methodName](...args)
  },
  getAndroidAsyncMethodStub: (jsInterfaceName, methodName) => {
    return (...args) => new Promise((resolve, reject) => {
      let callbackId = androidInterfaces.basicJsInterface.getUuid()
      androidAsyncMethodCallbackUtils.addCallback(callbackId, resolve, reject)
      androidInterfaces.asyncTaskJsInterface.invokeAsyncMethod(jsInterfaceName, methodName, callbackId, JSON.stringify(args))
    })
  }
}

export default androidInterfacesCompanion