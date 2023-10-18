<template>
  <el-dialog :visible.sync="showOut" @close="closeDialog" width="40em">
    <template slot="title">
      <h3>创建新题目</h3>
    </template>
    <el-form :model="form" label-width="5em" label-position="right">
      <el-form-item>
        <span slot="label" class="text-bold">题型</span>
        <el-radio-group v-model="type" size="small">
          <el-radio-button label="单选题"></el-radio-button>
          <el-radio-button label="多选题"></el-radio-button>
          <el-radio-button label="判断题"></el-radio-button>
          <el-radio-button label="填空题"></el-radio-button>
          <el-radio-button label="应用题"></el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <span slot="label" class="text-bold">操作</span>
        <el-row>
          <el-col :offset="5" :span="4">
            <el-button icon="el-icon-edit-outline" @click="innerContent = true" size="small">编辑题干</el-button>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-button icon="el-icon-edit" @click="innerAnswer = true" size="small">编辑答案</el-button>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-button icon="el-icon-picture-outline" size="small" disabled>编辑图片</el-button>
          </el-col>
        </el-row>
        <el-dialog :visible.sync="innerContent" append-to-body>
          <template slot="title">
            <h3>编辑题干</h3>
          </template>
          <el-input type="textarea" autosize
                    placeholder="请输入题干,使用半角{}代表空格"
                    v-model="tempContent" maxlength="255" show-word-limit></el-input>
          <div slot="footer" class="dialog-footer">
            <el-button @click="innerContent = false; tempContent = form.content">取 消</el-button>
            <el-button type="primary" @click="completeContent">完 成</el-button>
          </div>
        </el-dialog>
      </el-form-item>
    </el-form>
    <el-divider>
      <span style="color: #909399;font-size: small">题目预览</span>
    </el-divider>
    <el-row>
      <el-col :offset="1" :span="23" style="text-align: start">
        <question-type-tag :name="form.type"></question-type-tag>
        <span>&nbsp;</span>
        <question-blank class="text-bold" :content="form.content"></question-blank>
      </el-col>
    </el-row>
    <el-row>
      <el-col :offset="1" :span="23" style="text-align: start">
        <question-option :answer.sync="form.answer" :type="form.type"></question-option>
      </el-col>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeDialog(false)" size="medium">取 消</el-button>
      <el-button type="primary" @click="closeDialog(true)" size="medium">确 定</el-button>
    </div>
    <el-dialog :visible.sync="innerAnswer" append-to-body>
      <template slot="title">
        <h3>编辑答案</h3>
      </template>
      <el-form label-width="3em" :hide-required-asterisk="true" size="mini">
        <el-form-item v-show="form.type === '单选题' || form.type === '多选题'" v-for="(answer, index) in tempAnswer"
                      :key="answer.key" :rules="{required: true, message: '选项不能为空', trigger: 'blur'}">
          <el-tag slot="label" size="mini" effect="dark">{{ answer.id }}</el-tag>
          <el-row>
            <el-col :offset="1" :span="18">
              <el-input v-model="answer.content" :placeholder="'请输入' + answer.id + '选项'" :clearable="true"></el-input>
            </el-col>
            <el-col :offset="1" :span="4">
              <el-button-group>
                <el-button v-show="answer.correct" type="success" class="el-icon-check"
                           @click.prevent="answer.correct = !answer.correct; editList(index)"></el-button>
                <el-button v-show="!answer.correct" type="danger" class="el-icon-close"
                           @click.prevent="answer.correct = !answer.correct; editList(index)"></el-button>
                <el-button type="danger" class="el-icon-delete" @click.prevent="removeAnswer(index)"></el-button>
              </el-button-group>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item v-show="form.type === '判断题'">
          <el-row>
            <el-col :offset="3" :span="16">
              <el-radio-group v-model="tempAnswer">
                <el-radio :label="true">正确</el-radio>
                <el-radio :label="false">错误</el-radio>
              </el-radio-group>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item v-show="form.type === '填空题'" v-for="(answer, index) in tempAnswer"
                      :key="index" :rules="{required: true, message: '答案不能为空', trigger: 'blur'}">
          <el-tag slot="label" size="mini" effect="dark">{{ index + 1 }}</el-tag>
          <el-row>
            <el-col :offset="1" :span="22">
              <el-input v-model="tempAnswer[index]" :placeholder="'请输入答案，使用半角||分割多种答案'"
                        type="textarea" show-word-limit maxlength="255" autosize :clearable="true"></el-input>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item v-show="form.type === '应用题'">
          <el-tag slot="label" size="mini" effect="dark">答案</el-tag>
          <el-row>
            <el-col :offset="1" :span="22">
              <el-input v-model="tempAnswer" :placeholder="'请输入答案，使用半角||分割多种答案'"
                        type="textarea" show-word-limit maxlength="255" autosize :clearable="true"></el-input>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-show="form.type === '单选题' || form.type === '多选题'" @click="addAnswer" size="medium">添加选项</el-button>
        <el-button @click="resetAnswer" size="medium">重置</el-button>
        <el-button @click="innerAnswer = false; tempAnswer = this.form.answer" size="medium">取 消</el-button>
        <el-button type="primary" @click="completeAnswer" size="medium">完 成</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
import QuestionBlank from '@/components/bank/QuestionBlank'
import QuestionTypeTag from '@/components/bank/QuestionTypeTag'
import QuestionKeys from '@/components/bank/QuestionKeys'
import QuestionOption from '@/components/bank/QuestionOption'

export default {
  components: { QuestionOption, QuestionKeys, QuestionBlank, QuestionTypeTag },
  props: {
    showFlag: Boolean
  },
  data() {
    return {
      showOut: false,
      form: {
        type: '单选题',
        content: '示例题干',
        answer: []
      },
      innerContent: false,
      innerAnswer: false,
      tempContent: '示例题干',
      tempAnswer: [],
      type: '单选题'
    }
  },
  created() {
    this.resetAnswer()
  },
  methods: {
    initAnswer() {
      switch (this.form.type) {
        case '单选题':
        case '多选题':
          return [
            { id: 'A', content: 'A选项', correct: false },
            { id: 'B', content: 'B选项', correct: false },
            { id: 'C', content: 'C选项', correct: false },
            { id: 'D', content: 'D选项', correct: false },
          ]
        case '判断题':
          return true
        case '填空题':
          return Array(this.form.content.split(/{}/).length - 1).fill('')
        case '应用题':
        default:
          return ''
      }
    },
    resetAnswer() {
      this.tempAnswer = this.initAnswer()
      this.form.answer = this.initAnswer()
    },
    closeDialog(finished) {
      this.$emit('closeCreateDialog', finished ? this.form : null)
      this.tempContent = '示例题干'
      this.type = '单选题'
      this.resetAnswer()
      this.form.content = this.tempContent
      this.form.type = this.type
    },
    addAnswer() {
      if (this.tempAnswer.length >= 26) {
        this.$message.error('选项数量过多')
        return
      }
      let ch = String.fromCharCode('A'.charCodeAt(0) + this.tempAnswer.length)
      this.tempAnswer.push({
        id: ch,
        content: ch + '选项',
        correct: false
      })
    },
    removeAnswer(index) {
      this.tempAnswer.splice(index, 1)
      this.tempAnswer.forEach((currentValue, index) => {
        currentValue.id = String.fromCharCode('A'.charCodeAt(0) + index)
      })
    },
    completeAnswer() {
      this.innerAnswer = false;
      this.form.answer = JSON.parse(JSON.stringify(this.tempAnswer))
      this.tempAnswer = JSON.parse(JSON.stringify(this.form.answer))
    },
    completeContent() {
      this.innerContent = false
      this.form.content = this.tempContent
      if (this.form.type === '填空题') {
        let length = this.form.content.split(/{}/).length - 1
        if (length < this.form.answer.length) {
          this.form.answer.splice(0, length)
        } else if (length > this.form.answer.length) {
          this.form.answer = this.form.answer.concat(Array(length - this.form.answer.length).fill(''))
          this.tempAnswer = this.form.answer
        }
      }
    },
    setCorrect(index) {
      if (this.form.type !== '单选题') {
        this.tempAnswer[index].correct = !this.tempAnswer[index].correct
        return
      }
      let tmp = this.tempAnswer[index].correct
      this.tempAnswer.forEach(value => value.correct = false)
      this.tempAnswer[index].correct = !tmp
    },
    editList(index) {
      if (this.type === '单选题' && this.tempAnswer[index].correct) {
        this.tempAnswer.forEach(item => item.correct = false)
        this.tempAnswer[index].correct = true
      }
    }
  },
  watch: {
    showFlag() {
      this.showOut = this.showFlag
    },
    type() {
      this.form.type = this.type
      this.resetAnswer()
    }
  }
}
</script>

<style scoped>
.text-bold {
  font-weight: bold;
}
</style>