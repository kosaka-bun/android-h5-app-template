import codeUtils from '@/utils/code'
import { jsInterfaceUtils } from '@/utils/androidJsInterfaces'

const methodDefinitions = {
  openNewWebActivity: path => {
    window.location.href = path
  },
  test: () => {},
  asyncMethodTest: {
    isAsync: true,
    fallback: async (a, b) => {
      await codeUtils.sleep(3000)
      return {
        sum: a + b
      }
    }
  }
}

const basicJsInterface = jsInterfaceUtils.getJsInterfaceStub(
  'BasicJsInterface', methodDefinitions
) ?? methodDefinitions

export default basicJsInterface