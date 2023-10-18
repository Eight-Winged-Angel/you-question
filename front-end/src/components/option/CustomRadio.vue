<template>
  <div @click="handleClick" class="radio">
    <el-row style="display:flex; margin-bottom: 0.2em; align-items: center">
      <el-col :span="2">
        <el-tag :effect="isChecked ? 'dark' : 'plain'" size="mini"
                :type="type === '多选题' ? 'success':'primary'">
          {{ option.id }}
        </el-tag>
      </el-col>
      <el-col :span="22" class="content">
        <span>{{ option.content }}</span>
      </el-col>
    </el-row>
  </div>
</template>

<script>
let isChecked = false
export default {
  props: {
    option: {
      type: Object, default: () => {
        return { id: 'A', content: 'A选项' }
      }
    },
    type: { type: String, default: '单选题' },
    checked: { type: Array, default: () => [] }
  },
  mounted(){
    this.showAnswer()
  },
  data() {
    let checkedList = this.checked
    let isChecked = false
    return {
      isChecked,
      checkedList
    }
  },
  methods: {
    handleClick() {
      this.isChecked = !this.isChecked;
      if ((this.type === '单选题' || this.type === '判断题') && this.isChecked) {
        this.checkedList = [this.option.id]
      } else if (this.isChecked) {
        this.checkedList.push(this.option.id)
      } else {
        this.checkedList.splice(this.checked.indexOf(this.option.id), 1)
      }
      this.$emit('update:checked', this.checkedList)
    },
    showAnswer(){
      if (this.checkedList.indexOf(this.option.id) === -1) {
        this.isChecked = false;
      }
      else{
        this.isChecked = true;
      }
    }
  },
  watch: {
    checked() {
      this.checkedList = this.checked
      console.log("customRadio当前",this.checkedList,this.checkedList.indexOf(this.option.id))
      this.showAnswer();
    }
  }
};
</script>

<style scoped>
.radio:hover {
  cursor: pointer;
}

.content {
  display: flex;
  justify-items: flex-start;
}
</style>