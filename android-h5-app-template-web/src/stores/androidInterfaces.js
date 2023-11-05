const androidInterfaces = {
  basicJsInterface: window['android_BasicJsInterface'] ?? {
    test: unImplemented
  }
}

function unImplemented() {
  throw new Error('unImplemented')
}

export default androidInterfaces