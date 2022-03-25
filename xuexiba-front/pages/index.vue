<template>
  <div>
    <!-- <div>
      <div class="swiper-wrapper">
        <div class="swiper-slide" style="background: #040b1b">
          <a target="_blank" href="/">
            <img src="~/assets/photo/banner/index.jpg" alt="首页banner" />
          </a>
        </div>
      </div>
    </div> -->

    <div id="aCoursesList">
      <!-- 网校课程 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门课程</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list"  >
              <ul class="of" id="bna">
                <li v-for="c in courseList1" :key="c.id"  style="">
                  <div class="cc-l-wrap">
                    <section class="course-img" style="height:200px">
                      <img
                      style="height:200px"
                        :src="c.cover"
                        class="img-responsive"
                        :alt="c.title"
                      />
                      <div class="cc-mask">
                        <a :href="'/course/'+c.id" title="开始学习" class="comm-btn c-btn-1"
                          >开始学习</a
                        >
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a
                        :href="'/course/'+c.id"
                        :title="c.title"
                        class="course-title fsize18 c-333"
                        >{{c.title}}</a
                      >
                    </h3>
                    <section class="mt10 hLh20 of">
                      <span class="fr jgTag bg-green" v-if="Number(c.price)===0">
                        <i class="c-fff fsize12 f-fA">免费</i>
                      </span>
                      <span class="fr jgTag bg-green" v-if="Number(c.price)!=0">
                        <i class="c-fff fsize12 f-fA">￥{{c.price}}</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">{{c.viewCount}}人学习</i>
                      </span>
                    </section>
                  </div>
                </li>
               
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="/course" title="全部课程" class="comm-btn c-btn-2">全部课程</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校课程 结束 -->
      <!-- 网校名师 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">名师大咖</span>
            </h2>
          </header>
          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="t in teacherList1" :key="t.id">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href="'/teacher/'+t.id" :title="t.name">
                        <img
                          :alt="t.name"
                          :src="t.avatar"
                        />
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a :href="'/teacher/'+t.id" :title="t.name" class="fsize18 c-666"
                        >{{t.name}}</a
                      >
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999"
                        >{{t.intro}}</span
                      >
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">
                        {{t.career}}
                      </p>
                    </div>
                  </section>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="/teacher" title="全部讲师" class="comm-btn c-btn-2">全部讲师</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校名师 结束 -->
    </div>
  </div>
</template>
<script>
import course from "@/api/course/course";
import teacher from "@/api/teacher/teacher";
export default {
  data() {
    return {
      teacherList1: [],
      courseList1: []
    };
  },
  created() {
    this.teacherl();
    this.coursel();
    
  },
  methods: {
    teacherl() {
      teacher.getTeacherList().then((response) => {
        this.teacherList1 = response.data.data.teacherList;
      });
    },

    coursel() {
      course.getCourseList().then((response) => {
        this.courseList1 = response.data.data.courseList;
        console.log(this.courseList1);
      });
    },
  },
};
</script>