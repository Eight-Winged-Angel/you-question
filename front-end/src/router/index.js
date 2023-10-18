import VueRouter from 'vue-router'

const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

// 创建并暴露一个路由器
const router = new VueRouter({
  mode: "history",
  base: "you",
  routes: [
    // { path: '/user', component: () => import('@/views/PageAccount') },
    {
      path: '/', component: () => import('@/views/PageHome'), children: [
        {
          name: 'RegisterSteps1',
          path: 'registersteps1',
          component: () => import('@/components/register_steps/RegisterSteps1')
        },
        {
          name: 'RegisterSteps2',
          path: 'registersteps2',
          component: () => import('@/components/register_steps/RegisterSteps2')
        },
        {
          name: 'RegisterSteps3',
          path: 'registersteps3',
          component: () => import('@/components/register_steps/RegisterSteps3')
        }]
    },
    //children:[{path:'/search', component:() => import('@/components/CustomSearch')}]
    { path: '/login', component: () => import('@/components/CustomLogin') },

    { path: '/login', component: () => import('@/views/Login') },
    { path: '/main', component: () => import('@/views/Main') },
    {
      path: '/student', component: () => import('@/views/PageStudent'),
      children: [
        {
          path: 'course/:id', component: () => import('@/views/studentCourse/BaseStudentCourse'),
          children: [
            { name: 'oneexam', path: 'oneexam', component: () => import('@/views/OneExam') },
            { name: 'coursepractice', path: 'coursepractice/:id', component: () => import('@/views/CoursePractice') }
          ]
        },
        { path: 'account', component: () => import('@/views/account/StudentAccount') }
      ]
    },
    {
      path: '/teacher', component: () => import('@/views/PageTeacher'),
      children: [
        { path: 'course/:id', component: () => import('@/views/teacherCourse/BaseTeacherCourse') },
        { path: 'bank/question', component: () => import('@/views/bank/PageQuestion') },
        { path: 'bank/practice', component: () => import('@/views/bank/PagePractice') },
        { path: 'account', component: () => import('@/views/account/TeacherAccount') }
      ]
    },
    { path: '/coursebuild', component: () => import('@/views/CourseBuild') }

  ]
});


// router.beforeEach((to, from, next) => {
//   console.log(to.path)
//   if (to.path === '/login' || to.path === '/register') {
//     next();
//   } else {
//     let token = window.sessionStorage.getItem('token');
//     if (token === null || token === '') {
//       alert("请登录")
//       next('/login');
//     } else {
//       next();
//     }
//   }
// });

export default router
