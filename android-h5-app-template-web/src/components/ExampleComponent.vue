<template>
  <div>
    <p>
      <span>Element Plus Test:</span>
      <el-button @click="elementPlusTest">Test</el-button>
    </p>
    <p>
      <span>Vant Test:</span>
      <van-button plain type="primary" @click="vantTest">Vant Button</van-button>
    </p>
    <p>
      <span>Android JavaScript Interface:</span>
      <el-button @click="androidInterfaceTest">Test</el-button>
    </p>
    <p>
      <span>开启一个新的WebActivity：</span>
      <el-button @click="openNewWebActivityTest">开启</el-button>
    </p>
    <p>
      <span>Android Asynchronous Method:</span>
      <el-button @click="androidAsyncMethodTest" :loading="status.androidAsyncMethodTestLoading">Test</el-button>
    </p>
  </div>
</template>

<script setup>
import messageUtils from '@/utils/message'
import { showToast } from 'vant'
import androidInterfaces from '@/utils/androidInterfaces'
import { reactive } from 'vue'

const status = reactive({
  androidAsyncMethodTestLoading: false
})

function elementPlusTest() {
  messageUtils.success('Test')
}

function vantTest() {
  showToast('Test')
}

function androidInterfaceTest() {
  androidInterfaces.basicJsInterface.test()
}

function openNewWebActivityTest() {
  androidInterfaces.basicJsInterface.openNewWebActivity('/another')
}

function androidAsyncMethodTest() {
  let a = Math.floor(Math.random() * 100) + 1
  let b = Math.floor(Math.random() * 100) + 1
  status.androidAsyncMethodTestLoading = true
  messageUtils.success(`${a} + ${b}`)
  androidInterfaces.basicJsInterface.asyncMethodTest(a, b).then(res => {
    messageUtils.success(`sum: ${res.sum}`)
  }).finally(() => {
    status.androidAsyncMethodTestLoading = false
  })
}
</script>

<style scoped lang="scss">
p {
  display: flex;
  align-items: center;

  span {
    margin-right: 1em;
  }
}
</style>