import Vue from 'vue'
import Vuex from 'vuex'
import indexOne from "./indexOne"
import VueXAlong from 'vuex-along'

Vue.use(Vuex)

const actions = {
    changeLog(context, value) {
        context.commit('CHANGELOG', value)
    }
}

//准备mutations——用于操作数据（state）
const mutations = {
    CHANGELOG(state, user) {
        state.Authorization = user.Authorization;
        localStorage.setItem('Authorization', user.Authorization);
        if (state.loginState == '登录') {
            state.loginState = '已登录'
        }
        else {
            state.loginState = '登录'
        }

    },
    SETCOUERSID(state, id) {
        state.currentCourseID = id;
    },
    SETPTD(state,tableData){
        state.practiceTableData = tableData;
    },
    SETIDENTITY(state, identity){
        state.identity = identity;
    },
    GET_TOKEN(state,value){
        console.log("mutatttttttion中的gettoken被调用了")
        state.token = value;
    },
    GET_COURSEID(state,value){
        console.log("mutatttttttion中的getCOURSEID被调用了")
        state.courseId = value;
    },
    GET_NOTICEID(state,value){
        console.log("mutatttttttion中的getNOTICEID被调用了")
        state.noticeId = value;
    },
    SET_PAPER(state, value){
        state.paper = value;
    },
    SET_PRACTICEID(state,value){
        state.practiceId = value;
    },
    GET_SELECTED(state,value){
        console.log("mutatttttttion中的getselected被调用了")
        state.notSelected = value;
    }
}

//准备state——用于存储数据
const state = {
    token:'',
    courseId:'',
    noticeId:'',
    notSelected:false,
    loginState: '登录',
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : '',
    currentCourseID: "",
    identity:"teacher", //写死教师
    paper:[],
    practiceId:'',
}

//准备getters——用于将state中的数据进行加工
const getters = {

}

//创建并暴露store
export default new Vuex.Store({
    actions,
    mutations,
    state,
    getters,
    strict:false,
    modules:{
        indexOne
    },
    
plugins: [VueXAlong({
        name: 'along',     //存放在localStroage或者sessionStroage 中的名字
        local: false,      //是否存放在local中  false 不存放 如果存放按照下面session的配置配
        session: { list: [], isFilter: true }   
        //如果值不为false 那么可以传递对象 其中 当isFilter设置为true时， list 数组中的值就会被过滤调,这些值不会存放在seesion或者local中
      })]
})