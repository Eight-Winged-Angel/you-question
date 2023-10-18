<template>
  <div v-if="type === '单选题' || type === '多选题'">
    <el-divider></el-divider>
    <custom-radio-group :contents="thisAnswer" :type="type"></custom-radio-group>
  </div>
  <div v-else></div>
</template>

<script>
import CustomRadioGroup from '@/components/option/CustomRadioGroup'

export default {
  components: { CustomRadioGroup },
  props: ['answer', 'type'],
  data() {
    let thisAnswer
    return {
      thisAnswer
    }
  },
  created() {
    this.thisAnswer = this.answer
  },
  watch: {
    answer() {
      this.thisAnswer = this.answer
    }
  }
}
</script>

<style scoped>

</style>