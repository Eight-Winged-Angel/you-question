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
                <el-input placeholder="输入关键字以搜索" prefix-icon="el-icon-search" v-model="keyWord" 
                clearable @clear="searchList">
                  <el-button slot="append" icon="el-icon-search" @click="searchList"></el-button>
                </el-input>
              </el-form-item>
            </el-form>
          </el-col>
          <el-col :span="8">
            <el-button type="success" @click="showForm = true" icon="el-icon-circle-plus-outline">创建新练习</el-button>
          </el-col>
        </el-row>
      </el-header>
      <el-main style="padding-top: 3em;width:95%">
        <el-table :data="tableBank" style="width: 100%">
          <el-table-column width="10"></el-table-column>
          <el-table-column label="ID" width="100" prop="id"></el-table-column>
          <el-table-column label="内容" width="450">
            <template v-slot="scope">
              <el-popover placement="top" trigger="hover">
                <el-descriptions :column="3" size="mini" border>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-edit"></i>
                      题量
                    </template>
                    {{ scope.row.count }}道
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-edit-outline"></i>
                      满分
                    </template>
                    {{ scope.row.score }}分
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-share"></i>
                      邀请码
                    </template>
                    {{ scope.row.inviteCode }}
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-date"></i>
                      开始时间
                    </template>
                    {{ scope.row.startTime || '无限制' }}
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-time"></i>
                      限时
                    </template>
                    {{ scope.row.timeLimit || '无限制' }}
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-notebook-1"></i>
                      所属课程
                    </template>
                    {{ scope.row.course || '暂无' }}
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-date"></i>
                      结束时间
                    </template>
                    {{ scope.row.endTime || '无限制' }}
                  </el-descriptions-item>
                  <el-descriptions-item>
                    <template slot="label">
                      <i class="el-icon-bell"></i>
                      说明
                    </template>
                    {{ scope.row.content || '暂无' }}
                  </el-descriptions-item>
                </el-descriptions>
                <el-row slot="reference" class="text-bold">
                  <el-col>{{ scope.row.title }}</el-col>
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
        <practice-create-dialog :show-flag="showForm" @closeCreateDialog="closeCreateDialog"></practice-create-dialog>

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
import PracticeCreateDialog from '@/components/bank/PracticeCreateDialog'
import axios from 'axios'

export default {
  components: { PracticeCreateDialog, BaseBreadcrumb },
  data() {
    return {
      keyWord: '',
      breadcrumbItems: ['我的题库', '练习'],
      showForm: false,
      tableBank: [],
      total:0,
      pagenum:1,
    }
  },
  created() {
    axios.get('http://localhost:14514/teacher/bank/practice/list', {
      headers: {
        'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
      },
      params:{
        keyWord:this.keyWord,
        page:this.pagenum
      }
    }).then(res => {
      if (res.data.code === '00000') {
        this.total = res.data.data.pages;
        this.tableBank = res.data.data.records.map(item => {
          return {
            id: item.id,
            title: item.title,
            date: item.date,
            inviteCode: item.inviteCode,
            count: item.count,
            score: item.score,
            content: item.content,
            course: item.course,
            startTime: item.startTime,
            endTime: item.endTime,
            timeLimit: this.parseTime(item.seconds)
          }})
        return
      }
      this.$notify.error({
        title: '查询题库失败',
        message: res.data.message
      })
    })
  },
  methods: {
    searchList(){
      axios.get('http://localhost:14514/teacher/bank/practice/list', {
      headers: {
        'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
      },
      params:{
        page:this.pagenum,
        keyWord:this.keyWord
      }
    }).then(res => {
      if (res.data.code === '00000') {
        this.total = res.data.data.pages;
        this.tableBank = res.data.data.records.map(item => {
          return {
            id: item.id,
            title: item.title,
            date: item.date,
            inviteCode: item.inviteCode,
            count: item.count,
            score: item.score,
            content: item.content,
            course: item.course,
            startTime: item.startTime,
            endTime: item.endTime,
            timeLimit: this.parseTime(item.seconds)
          }})
        return
      }
      this.$notify.error({
        title: '查询题库失败',
        message: res.data.message
      })
    })
    },
    handleCurrentChange(newPage){
        this.pagenum = newPage
        console.log(newPage,this.pagenum,"w调用getstudentlist")
        
      axios.get('http://localhost:14514/teacher/bank/practice/list', {
      headers: {
        'token': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMDAwMiJ9.m4m9aaAzqSk6KLo3vIOCKTFn1VjIX4u_sNYZ9tOmv2g'
      },
      params:{
        page:this.pagenum,
        keyWord:this.keyWord
      }
    }).then(res => {
      if (res.data.code === '00000') {
        this.total = res.data.data.pages;
        this.tableBank = res.data.data.records.map(item => {
          return {
            id: item.id,
            title: item.title,
            date: item.date,
            inviteCode: item.inviteCode,
            count: item.count,
            score: item.score,
            content: item.content,
            course: item.course,
            startTime: item.startTime,
            endTime: item.endTime,
            timeLimit: this.parseTime(item.seconds)
          }})
        return
      }
      this.$notify.error({
        title: '查询题库失败',
        message: res.data.message
      })
    })


      },
    closeCreateDialog() {
      this.showForm = false
    },
    parseTime(seconds) {
      let hour = 0
      let minute = 0
      while (seconds >= 3600) {
        seconds -= 3600
        ++hour
      }
      while (seconds >= 60) {
        seconds -= 60
        ++minute
      }
      let res = ''
      if (hour > 0) {
        res += `${hour}h`
      }
      if (minute > 0) {
        res += `${minute}min`
      }
      if (seconds > 0) {
        res += `${seconds}s`
      }
      return res
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