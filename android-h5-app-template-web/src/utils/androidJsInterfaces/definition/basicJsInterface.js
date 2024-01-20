import codeUtils from '@/utils/code'
import { jsInterfaceUtils } from '@/utils/androidJsInterfaces'

const basicJsInterface = jsInterfaceUtils.getJsInterfaceStub('BasicJsInterface', {
  openNewWebActivity: path => {
    jsInterfaceUtils.jsInterfaceWarning()
    window.location.href = path
  },
  getUuid: () => codeUtils.randomUUID(),
  test: jsInterfaceUtils.emptyImplementation(),
  asyncMethodTest: {
    isAsync: true,
    fallback: (a, b) => {
      jsInterfaceUtils.jsInterfaceWarning()
      return {
        sum: a + b
      }
    }
  }
})

export default basicJsInterface