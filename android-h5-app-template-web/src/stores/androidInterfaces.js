const androidInterfaces = {
  basicJsInterface: window['android_BasicJsInterface'] ?? {
    openNewWebActivity: unImplemented,
    test: unImplemented
  }
}

function unImplemented() {
  throw new Error('unImplemented')
}

export default androidInterfaces