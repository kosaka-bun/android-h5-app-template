import testInterfaceStub from '@/android-interfaces/stub/test'
import defaultAndroidInterfaces from '@honoka/js-utils/src/android/async-interface'

const androidInterfaces = {
  ...defaultAndroidInterfaces,
  test: testInterfaceStub
}

export default androidInterfaces
