<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a href="#" title class="c-999 fsize14">{{
          courseWebVo.subjectLevelOne
        }}</a>
        \
        <span class="c-333 fsize14">{{ courseWebVo.subjectLevelTwo }}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px">
          <section class="p-h-video-box" id="videoPlay">
            <img
              height="357px"
              width="700px"
              :src="courseWebVo.cover"
              :alt="courseWebVo.title"
              class="dis c-v-pic"
            />
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{ courseWebVo.title }}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size: 24px"
                >￥{{ courseWebVo.price }}</b
              >
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14"
                >主讲： {{ courseWebVo.teacherName }}&nbsp;&nbsp;&nbsp;</span
              >
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#">收藏</a>
              </span>
            </section>
            <section
              class="c-attr-mt"
              v-if="Number(courseWebVo.price) === 0 || this.isBuy == true"
            >
              <a href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
            </section>
            <section
              class="c-attr-mt"
              v-if="Number(courseWebVo.price) != 0 && this.isBuy == false"
            >
              <a
                @click="createOrders()"
                title="立即购买"
                class="comm-btn c-btn-3"
                >立即购买</a
              >
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{ courseWebVo.buyCount }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{ courseWebVo.lessonNum }}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{ courseWebVo.viewCount }}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="courseWebVo.description">
                        {{ courseWebVo.description }}
                      </p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <section
                          class="no-data-wrap"
                          v-if="this.chapterVideoList.length === 0"
                        >
                          <span class="c-666 fsize14 ml10 vam"
                            >正在快马加鞭整理中，嘚嘚嘚O(∩_∩)O</span
                          >
                        </section>

                        <ul>
                          <!-- 文件目录 -->
                          <li
                            class="lh-menu-stair"
                            v-for="chapter in chapterVideoList"
                            :key="chapter.id"
                          >
                            <a
                              href="javascript: void(0)"
                              :title="chapter.title"
                              class="current-1"
                            >
                              <em class="lh-menu-i-1 icon18 mr10"></em
                              >{{ chapter.title }}
                            </a>

                            <ol class="lh-menu-ol" style="display: block">
                              <li
                                class="lh-menu-second ml30"
                                v-for="video in chapter.children"
                                :key="video.id"
                              >
                                <a
                                  :href="'/player/' + video.videoSourceId"
                                  target="_blank"
                                >
                                  <span class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em
                                  >{{ video.title }}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
              <aside class="fl col-3">
                <div style="width: 800px">
                  <div>
                    <section class="c-infor-tabTitle c-tab-title">
                      <h6 class="c-g-content c-infor-title">
                        <span>课程评价</span>
                        <button
                          type="button"
                          style="float: right"
                          @click="open()"
                        >
                          我要写评价
                        </button>
                      </h6>
                    </section>
                    <!-- /无数据提示 开始-->
                    <section class="no-data-wrap" v-if="this.total == 0">
                      <span class="c-666 fsize14 ml10 vam"
                        >咦~，好惨一课程，尽然没有小伙伴愿意给它评价，呜呜呜(ㄒoㄒ)</span
                      >
                    </section>
                    <!-- /无数据提示 结束-->
                    <section class="stud-act-list" v-if="this.total > 0">
                      <ul style="height: auto">
                        <li
                          class="lh-menu-stair"
                          v-for="c in this.comment"
                          :key="c.id"
                        >
                          <div class="u-face">
                            <img :src="c.avatar" width="50" height="50" alt />
                          </div>
                          <section class="hLh30 txtOf">
                            {{ c.nickname }}&nbsp;&nbsp;{{
                              c.gmtCreate
                            }}&nbsp;&nbsp;
                            <button
                              v-if="userId == c.memberId"
                              @click="removeComment(c.id)"
                            >
                              删除
                            </button>
                          </section>
                          <section class="hLh20 txtOf">
                            <span class="c-999">评价内容:{{ c.content }}</span>
                          </section>
                        </li>
                      </ul>
                      <!-- 公共分页 开始 -->
                      <!-- 分页条 -->
                      <el-pagination
                        background
                        :page-size="pageSize"
                        :current-page="pageNum"
                        style="text-align: center"
                        layout="total,prev, pager, next,jumper"
                        prev-text="上一页"
                        next-text="下一页"
                        :total="total"
                        @current-change="getComment"
                      >
                      </el-pagination>
                      <!-- 公共分页 结束 -->
                    </section>
                  </div>
                </div>
              </aside>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto">
                  <li>
                    <div class="u-face">
                      <a :href="'/teacher/' + courseWebVo.teacherId">
                        <img
                          :src="courseWebVo.avatar"
                          width="50"
                          height="50"
                          alt
                        />
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a
                        class="c-333 fsize16 fl"
                        :href="'/teacher/' + courseWebVo.teacherId"
                        >{{ courseWebVo.teacherName }}</a
                      >
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{ courseWebVo.intro }}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>

<script>
import course from "@/api/course/course";
import order from "@/api/order/orders";
export default {
  // asyncData({ params, error }) {
  //   this.courseId = params.id;
  //    this.getCourseData();
  //   this.getBuy()
  // },
  data() {
    return {
      courseId: "",
      courseWebVo: {},
      chapterVideoList: [],
      isBuy: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      comment: {},
      userId: "",
    };
  },
  created() {
    this.courseId = this.$route.params.id;

    this.getCourseData();
    this.getBuy();
    this.getComment();
    this.getUserId();
  },
  methods: {
    changetype() {
      this.type = "";
    },
    getBuy() {
      course.getBuy(this.courseId).then((response) => {
        this.isBuy = response.data.data.isBuy;
      });
    },

    getCourseData() {
      course.getCourseData(this.courseId).then((response) => {
        this.courseWebVo = response.data.data.courseDataVO;
        this.chapterVideoList = response.data.data.chapterVoList;
      });
    },

    //生成订单
    createOrders() {
      order.createOrders(this.courseId).then((response) => {
        console.log(this.courseId);
        //获取返回订单号
        //生成订单之后，跳转订单显示页面
        this.$router.push({
          path: window.open("/orders/" + response.data.data.orderId),
        });
      });
    },

    //评论发表
    open() {
      this.$prompt("快来分享你对此课程的看法吧！", "评论窗口", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false, //点击遮罩框可以推出
      })
        .then(({ value }) => {
          course
            .saveComment(this.courseWebVo.teacherId, this.courseId, value)
            .then((response) => {
              this.$message({
                type: "success",
                message: "评论成功",
              });
              // location.reload();
              this.getCourseData();
              this.getBuy();
              this.getComment();
              this.getUserId();
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消评论",
          });
        });
    },
    //显示评论
    getComment(page = 1) {
      this.pageNum = page;
      course
        .getComment(this.pageNum, this.pageSize, this.courseId)
        .then((response) => {
          this.total = response.data.data.total;
          this.comment = response.data.data.records;
        });
    },
    getUserId() {
      course.getUserId().then((response) => {
        this.userId = response.data.data.userId;
      });
    },
    removeComment(id) {
      this.$confirm("确定要删除该评价吗, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          console.log(id);
          course.removeComment(id).then((response) => {
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            // location.reload();
            this.getCourseData();
            this.getBuy();
            this.getComment();
            this.getUserId();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>
<style >
/* .header-img {
      display: inline-block;
      vertical-align: top;
    } */

.author-title:not(:last-child) {
  border-bottom: 1px solid rgba(74, 136, 199, 0.3);
}

.header-img {
  display: inline-block;
  vertical-align: top;
}

span {
  display: block;
  cursor: pointer;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.author-name {
  color: #000;
  font-size: 18px;
  font-weight: bold;
}
.author-time {
  font-size: 14px;
}

.reply {
  font-size: 16px;
  color: #000;
}

.reply-box {
  margin: 10px 0 0 50px;
  background-color: #efefef;
}
</style>

