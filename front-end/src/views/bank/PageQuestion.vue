<template>
  <el-container>
    <el-header style="width: 100%">
      <base-breadcrumb class="breadcrumb" :items="breadcrumbItems"></base-breadcrumb>
    </el-header>
    <el-container class="sub-container" style="margin-top:1%">
      <el-header>
        <el-row class="row-header" type="flex" justify="end" style="width: 95%">
          <el-col :span="8">
            <el-form>
              <el-form-item>
                <el-input placeholder="输入关键字以搜索" prefix-icon="el-icon-search" v-model="keyWord" @input="search"
                clearable
                @clear="search">
                  <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
                </el-input>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="8">
            <el-button type="success" @click="showForm = true" icon="el-icon-circle-plus-outline">创建新题目</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="padding-top: 3em;width:95%">
        <el-table :data="tableBank" style="width: 100%">
          <el-table-column width="10"></el-table-column>
          <el-table-column label="ID" width="100" prop="id"></el-table-column>
          <el-table-column label="题型" width="80">
            <template v-slot="scope">
              <question-type-tag :name="scope.row.type" size="small"></question-type-tag>
            </template>
          </el-table-column>
          <el-table-column label="内容" width="430">
            <template v-slot="scope">
              <el-popover placement="top" width="360" trigger="hover">
                <el-row>
                  <el-col>
                    <question-type-tag :name="scope.row.type"></question-type-tag>
                    <span>&nbsp;</span>
                    <question-blank class="text-bold" :content="scope.row.content"></question-blank>
                  </el-col>
                </el-row>
                <question-option :type="scope.row.type" :answer="scope.row.answer"></question-option>
                <el-divider>
                  <span style="color: #909399;font-size: small">参考答案</span>
                </el-divider>
                <question-keys :type="scope.row.type" :answer="scope.row.answer"></question-keys>
                <el-row slot="reference" class="text-bold">
                  <el-col>
                    <question-blank :content="scope.row.content"></question-blank>
                  </el-col>
                </el-row>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="图片" width="60">
            暂无
          </el-table-column>
          <el-table-column label="创建日期" width="150">
            <template v-slot="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.date }}</span>
            </template>
          </el-table-column>
          <el-table-column width="40"></el-table-column>
        </el-table>
        <question-create-dialog :show-flag="showForm" @closeCreateDialog="closeCreateDialog"></question-create-dialog>

        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="this.pagenum"
            :page-sizes="[10]"
            :page-size="10"
            layout="total, sizes, prev, pager, next, jumper"
            :page-count="this.total"
            style="width:90%;margin-top:3%">
          <!-- 需要修改total，改为实际拥有的信息条数！！ -->
        </el-pagination>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import BaseBreadcrumb from '@/components/BaseBreadcrumb'
import QuestionTypeTag from '@/components/bank/QuestionTypeTag'
import QuestionKeys from '@/components/bank/QuestionKeys'
import QuestionBlank from '@/components/bank/QuestionBlank'
import QuestionCreateDialog from '@/components/bank/QuestionCreateDialog'
import QuestionOption from '@/components/bank/QuestionOption'
import axios from 'axios'

export default {
  components: { QuestionCreateDialog, QuestionBlank, QuestionKeys, BaseBreadcrumb, QuestionTypeTag, QuestionOption },
  data() {
    return {
      keyWord: '',
      breadcrumbItems: ['我的题库', '题目'],
      showForm: false,
      tableBank: [],
      total:0,
      pagenum:1,
    }
  },
  created() {
    this.search()
  },
  methods: {
    handleCurrentChange(newPage){
        this.pagenum = newPage
        console.log(newPage,this.pagenum,"w调用getstudentlist");
        this.search()
      },
    search() {
      axios.get('http://localhost:14514/teacher/bank/question/list', {
        headers: {
          'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
        },
        params: {
          'keyWord': this.keyWord,
          page:this.pagenum
        }
      }).then(res => {
        if (res.data.code === '00000') {
          this.total = res.data.data.pages;
          this.tableBank = res.data.data.records
          return
        }

        this.$notify.error({
          title: '查询题库失败',
          message: res.data.message
        })
      })
    },
    closeCreateDialog(form) {
      this.showForm = false
      if (form !== null) {
        axios.post('http://localhost:14514/teacher/bank/question/add', form, {
          headers: {
            'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
          }
        }).then(res => {
          if (res.data.code === '00000') {
            this.$notify.success({
              title: '创建成功',
              message: '快去我的题库看看吧'
            })
            return
          }

          this.$notify.error({
            title: '创建失败',
            message: res.data.message
          })
        })
      }
    }
  }
}
</script>

<style scoped>
.text-bold {
  font-weight: bold;
}

.row-header {
  background-color: #409EFF;
  border-radius: 0.5em;
  padding-top: 1.5em;
}

.breadcrumb {
  margin: 0.5em 0;
}

.sub-container {
  padding: 20px;
}
</style>