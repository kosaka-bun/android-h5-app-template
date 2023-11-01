<template>
  <div>
    <p>
      <span>Element Plus Test:</span>
      <el-button @click="elementPlusTest">Test</el-button>
    </p>
    <p>
      <span>Vant Test:</span>
      <van-button plain type="primary" @click="vantTest">朴素按钮</van-button>
    </p>
  </div>
</template>

<script setup>
import messageUtils from '@/utils/message'
import { showToast } from 'vant'

function elementPlusTest() {
  messageUtils.success('Test')
}

function vantTest() {
  showToast('Test')
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