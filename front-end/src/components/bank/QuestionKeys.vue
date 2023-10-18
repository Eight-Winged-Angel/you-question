<template>
  <div v-if="type === '单选题'">
    <el-row>
      <el-col :offset="1" :span="23">
        <el-row>
          <el-col :offset="1" :span="1" v-for="(value, key) in options" :key="key">
            <el-tag size="mini" effect="dark">{{ value.id }}</el-tag>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
  <div v-else-if="type === '多选题'">
    <el-row>
      <el-col :offset="1" :span="23">
        <el-row>
          <el-col :offset="1" :span="1" v-for="(value, key) in options" :key="key">
            <el-tag size="mini" effect="dark" type="success">{{ value.id }}</el-tag>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
  <div v-else-if="type === '判断题'">
    <el-row>
      <el-col :offset="1" :span="23">
        <el-row style="margin-bottom: 0.2em">
          <el-col :offset="1" :span="23">
            <el-tag v-if="answer" size="medium" effect="dark">正确</el-tag>
            <el-tag v-else size="medium" effect="dark" type="danger">错误</el-tag>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
  <div v-else-if="type === '填空题'">
    <el-row v-for="(value, index) in answer" :key="index">
      <el-col :offset="2" :span="23">
        <el-row style="margin-bottom: 0.2em">
          <el-col :span="2">
            <el-tag class="text-bold" size="mini" effect="dark">
              {{ index + 1 }}
            </el-tag>
          </el-col>
          <el-col :span="21">
            <span v-for="(v, i) in value.split('||')" :key="i">
              <el-divider direction="vertical" v-if="i > 0"></el-divider>
              <span>{{ v }}</span>
            </span>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
  <div v-else>
    <el-row style="margin-bottom: 0.2em">
      <el-col :offset="2" :span="23">
        {{ answer }}
      </el-col>
    </el-row>
  </div>
</template>

<script>
let answer
export default {
  data() {
    let options = []
    return {
      options
    }
  },
  props: {
    answer,
    type: { type: String }
  },
  created() {
    if (this.type === '单选题' || this.type === '多选题') {
      this.options = this.answer.filter(item => {
        if (item.correct) {
          return item
        }
      })
    }
  }
}
</script>

<style scoped>
.text-bold {
  font-weight: bold;
}
</style>