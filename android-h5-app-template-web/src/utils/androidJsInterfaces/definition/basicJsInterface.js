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
    fallback: async (a, b) => {
      jsInterfaceUtils.jsInterfaceWarning()
      await codeUtils.sleep(3000)
      return {
        sum: a + b
      }
    }
  }
})

export default basicJsInterface