<template>
  <el-dialog :visible.sync="showOut" @close="closeDialog" width="45em">
    <template slot="title">
      <h3>创建新练习</h3>
    </template>
    <el-form :model="form" label-width="5em" label-position="right">
      <el-form-item>
        <span slot="label" class="text-bold">标题</span>
        <el-input type="textarea" autosize placeholder="请输入标题" size="small"
                  v-model="form.title" maxlength="255" show-word-limit></el-input>
      </el-form-item>
      <el-form-item>
        <span slot="label" class="text-bold">说明</span>
        <el-input type="textarea" autosize placeholder="请输入说明" size="small"
                  v-model="form.content" maxlength="255" show-word-limit></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="15">
          <el-form-item>
            <span slot="label" class="text-bold">起止</span>
            <el-date-picker v-model="timeRange" type="datetimerange" range-separator="-" size="small"
                            start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :offset="1" :span="8">
          <el-form-item>
            <span slot="label" class="text-bold">限时</span>
            <el-tooltip class="item" content="格式：数字+小写英文字母单位（h-时、min-分、s-秒），最长4h" placement="top-end">
              <el-input placeholder="无限制" size="small" v-model="form.timeLimit" :clearable="true"></el-input>
            </el-tooltip>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <span slot="label" class="text-bold">选题</span>
        <el-row>
          <el-col :span="13">
            <el-descriptions style="padding-top: 0.4em" :column="2" size="mini" border>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-edit"></i>
                  已选题量
                </template>
                {{ count }}道
              </el-descriptions-item>
              <el-descriptions-item>
                <template slot="label">
                  <i class="el-icon-edit-outline"></i>
                  总分值
                </template>
                {{ score }}分
              </el-descriptions-item>
            </el-descriptions>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-button icon="el-icon-edit-outline" size="small" @click="innerQuestion = true">编辑题目</el-button>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-button icon="el-icon-picture-outline" size="small" disabled>编辑图片</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeDialog(false)" size="medium">取 消</el-button>
      <el-button type="primary" @click="closeDialog(true)" size="medium">确 定</el-button>
    </div>
    <el-dialog :visible.sync="innerQuestion" append-to-body width="60%">
      <template slot="title">
        <h3>编辑题目</h3>
      </template>
      <el-row type="flex" align="middle">
        <el-col :offset="1" :span="15" style="text-align: start">
          <el-row>
            <el-col :span="10">
              <span class="text-bold">当前题库：</span>
              <el-dropdown @command="handleCommand">
                <span style="color: #409EFF">
                  {{ global ? '全站题库' : '我的题库' }}<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="我的题库">我的题库</el-dropdown-item>
                  <el-dropdown-item command="全站题库">全站题库</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-col>
            <el-col :offset="2" :span="10">
              <span class="text-bold">题型：</span>
              <el-dropdown @command="handleType">
                <span style="color: #409EFF">
                  <question-type-tag :name="type"></question-type-tag>
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-for="(value, index) in ['无限制', '单选题', '多选题', '判断题', '填空题', '应用题']"
                                    :key="index" :command="value">
                    <question-type-tag :name="value"></question-type-tag>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-col>
          </el-row>
          <el-row style="margin-top: 1.2em" type="flex" justify="start">
            <el-col :span="18">
              <el-input size="small" prefix-icon="el-icon-search" placeholder="输入关键字以搜索" width="30em"
                        :clearable="true" v-model="keyWord" @input="bankSearch" @focus="bankSearch">
                <el-button slot="append" icon="el-icon-search"></el-button>
              </el-input>
            </el-col>
          </el-row>
        </el-col>
        <el-col :offset="2" :span="5">
          <el-descriptions style="padding-top: 0.4em" :column="1" size="mini" border>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-edit"></i>
                已选题量
              </template>
              {{ count }}道
            </el-descriptions-item>
            <el-descriptions-item>
              <template slot="label">
                <i class="el-icon-edit-outline"></i>
                总分值
              </template>
              {{ score }}分
            </el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
      <el-row style="text-align: start; margin: 2em">
        <el-col>
          <el-transfer v-model="tempQuestion" :data="bank" :props="{key: 'id', label: 'content'}"
                       :titles="[global ? '全站题库' : '我的题库', '已选题目']" @change="handleChange">
            <template slot-scope="scope">
              <el-popover placement="left" width="400" trigger="hover">
                <el-row>
                  <el-col>
                    <question-type-tag :name="scope.option.type"></question-type-tag>
                    <span>&nbsp;</span>
                    <question-blank class="text-bold" :content="scope.option.content"></question-blank>
                  </el-col>
                </el-row>
                <question-option :type="scope.option.type" :answer="scope.option.answer"></question-option>
                <el-divider>
                  <span style="color: #909399;font-size: small">参考答案</span>
                </el-divider>
                <question-keys :type="scope.option.type" :answer="scope.option.answer"></question-keys>
                <span slot="reference">
                  <span v-show="scope.option.score" class="text-bold"
                        style="color: #909399">[{{ scope.option.score }}分]
                  </span>
                  <question-blank class="text-bold" :content="scope.option.content"></question-blank>
                </span>
              </el-popover>
            </template>
            <el-row slot="left-footer">
              <el-col class="text-bold" :span="8" style="font-size: smaller">
                设定分值：
              </el-col>
              <el-col :offset="1" :span="15">
                <el-input-number v-model="perScore" controls-position="right" size="mini"></el-input-number>
              </el-col>
            </el-row>
            <el-row slot="right-footer" type="flex" justify="end">
              <el-col :span="8">
                <el-button size="mini" icon="el-icon-delete" type="danger" @click="clear"></el-button>
              </el-col>
            </el-row>
          </el-transfer>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="completeQuestion" size="medium">完 成</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
import QuestionTypeTag from '@/components/bank/QuestionTypeTag'
import QuestionBlank from '@/components/bank/QuestionBlank'
import QuestionOption from '@/components/bank/QuestionOption'
import QuestionKeys from '@/components/bank/QuestionKeys'
import axios from 'axios'

export default {
  components: { QuestionBlank, QuestionTypeTag, QuestionOption, QuestionKeys },
  props: {
    showFlag: Boolean
  },
  data() {
    return {
      showOut: false,
      innerQuestion: false,
      global: false,
      type: '无限制',
      form: {
        title: '示例标题',
        timeLimit: '2h',
        content: '示例说明',
        question: []
      },
      tempQuestion: [],
      selectedBank: [],
      selectedId: [],
      bank: [],
      keyWord: '',
      perScore: 1,
      timeRange: []
    }
  },
  computed: {
    count() {
      return this.form.question.length
    },
    score() {
      return this.form.question.reduce((prev, cur) => prev + cur.score, 0)
    }
  },
  methods: {
    completeQuestion() {
      this.innerQuestion = false
    },
    bankSearch() {
      console.log("老师练习丘丘求求群群群群群");
      if (this.keyWord.trim().length === 0) {
        this.bank = this.selectedBank
        return
      }
      console.log("群群群");
      axios.get('http://localhost:14514/teacher/bank/question/list', {
        headers: {
          'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
        },
        params: {
          'keyWord': this.keyWord,
          'type': this.type
        }
      }).then(res => {
        if (res.data.code === '00000') {
          let data = res.data.data.records
          console.log("老师练习丘丘求求群群群群群",res)
          this.bank = this.selectedBank.concat(
              this.keyWord.trim().length === 0 ? data : data.filter(currentValue =>
                  currentValue.content.indexOf(this.keyWord) > -1)
                  .filter(item => this.selectedId.indexOf(item.id) === -1))
          return
        }

        this.$notify.error({
          title: '查询题库失败',
          message: res.data.message
        })
      })
    },
    handleCommand(command) {
      switch (command) {
        case '全站题库':
          this.global = true
          break
        case '个人题库':
        default:
          this.global = false
      }
      this.bankSearch()
    },
    handleType(type) {
      this.type = type
      this.bankSearch()
    },
    handleChange(currentList, direction, key) {
      if (direction === 'right') {
        key.map(item => {
          this.form.question.push({ id: item, score: this.perScore });
          this.selectedId.push(item)
        })
        this.selectedBank = this.selectedBank.concat(
            this.bank.filter((item) => {
              if (key.indexOf(item.id) > -1 && this.selectedBank.indexOf((item.id) === -1)) {
                item.score = this.perScore
                return true
              }
            }))
      } else if (direction === 'left') {
        this.form.question = this.form.question.filter(item => key.indexOf(item.id) === -1)
        this.selectedBank = this.selectedBank.filter(item => key.indexOf(item.id) === -1)
        this.bank = this.selectedBank
        this.selectedId = this.selectedId.filter(item => key.indexOf(item) === -1)
      }
    },
    closeDialog(finished) {
      this.$emit('closeCreateDialog')
      if (!finished) {
        return
      }

      axios.post('http://localhost:14514/teacher/bank/practice/add', {
        title: this.form.title,
        startTime: this.timeRange[0],
        endTime: this.timeRange[1],
        timeLimit: this.form.timeLimit,
        content: this.form.content,
        question: this.form.question
      }, {
        headers: {
          'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
        }
      }).then(res => {
        if (res.data.code === '00000') {
          this.$notify.success({
            title: '创建成功',
            message: '练习邀请码'+res.data.data
          })
          return
        }

        this.$notify.error({
          title: '创建失败',
          message: res.data.message
        })
      })
      this.form = {
        title: '示例标题',
        timeRange: [],
        timeLimit: '2h',
        content: '示例说明',
        question: []
      }
      this.global = false
      this.type = '无限制'
      this.tempQuestion = []
      this.selectedBank = []
      this.selectedId = []
      this.bank = []
      this.keyWord = ''
      this.perScore = 1
      this.timeRange = []
    },
    clear() {
      this.form.question = []
      this.selectedId = []
      this.selectedBank = []
      this.bank = []
      this.tempQuestion = []
    }
  },
  watch: {
    showFlag() {
      this.showOut = this.showFlag
    }
  }
}
</script>

<style scoped>
.text-bold {
  font-weight: bold;
}
</style>