<template>
  <header id="header">
    <section class="container">
      <el-backtop
        style="color: red; background: blue; bottom: 250px"
      ></el-backtop>

      <!-- <h1 id="logo">
          <a href="#" title="学习吧">
            <img src="~/assets/img/logo.png" width="100%" alt="学习吧" />
          </a>
        </h1> -->
      <el-button @click="drawer1 = true" type="primary" style="float: right">
        我也来写一篇文章
      </el-button>
      <el-drawer
        :visible.sync="drawer1"
        :direction="direction1"
        :before-close="handleClose1"
        style="height: 1900px"
      >
        <div class="index" style="padding: 20px">
          <el-form ref="form" :model="communityText" label-width="80px">
            <el-form-item label="文章标题">
              <el-input v-model="communityText.title"></el-input>
            </el-form-item>
            <el-form-item label="文章内容">
              <!-- 富文本编译器 -->

              <!-- client-only:声明只在浏览器渲染 -->

              <VueEditor v-model="communityText.text" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">立即发布</el-button>
              <el-button @click="reset">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-drawer>
      <div class="h-r-nsl">
        <section class="no-data-wrap" v-if="this.communityText1.length === 0">
          <span class="c-666 fsize14 ml10 vam"
            >呜呜呜~~~~，这里怎么空空如也！</span
          >
        </section>

        <li
          id="no-login"
          v-for="c in communityText1"
          :key="c.id"
          style="padding-bottom: 30px"
        >
          <img
            style="border-radius: 50%"
            :src="c.avatar"
            width="30"
            height="30"
            class="vam picImg"
            alt
          />

          <span class="vam ml5">{{ c.nickname }}</span>

          <span class="vam ml5">{{ c.gmtCreate }}</span>
          <a
            class="vam ml5"
            v-if="userId == c.publicId"
            @click="removeCommunityText(c.id)"
          >
            删除
          </a>
          <br />
          <span class="vam ml5" style="font-size: 20px; font-weight: bold"
            >标题：{{ c.title }}</span
          >
          <br />

          <p class="vam ml5" v-html="c.text">{{ c.text }}</p>
          <a @click="showComment(c.id)">点击显示评论</a>

          <a style="padding-left: 200px" @click="open(c.id)">写评论</a>
        </li>
      </div>
      <aside class="mw-nav-btn">
        <div class="mw-nav-icon"></div>
      </aside>
      <div class="clear"></div>
    </section>

    <el-drawer
      title="评论区"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose"
    >
      <section class="no-data-wrap" v-if="this.communityTextComment1 == ''">
        <em class="icon30 no-data-ico">&nbsp;</em>
        <span class="c-666 fsize14 ml10 vam"
          >这里空荡荡的，什么也没有/(ㄒoㄒ)/~~</span
        >
      </section>
      <section class="mt20" v-if="this.communityTextComment1 != ''">
        <div class="lh-menu-wrap">
          <menu id="lh-menu" class="lh-menu mt10 mr10">
            <ul>
              <!-- 文件目录 -->
              <li
                class="lh-menu-stair"
                v-for="com in this.communityTextComment1"
                :key="com.id"
              >
                <img
                  style="border-radius: 50%"
                  :src="com.avatar"
                  width="30"
                  height="30"
                  class="vam picImg"
                  alt
                />
                <span class="vam ml5">{{ com.nickname }}</span>

                <span class="vam ml5">{{ com.gmtModified }}</span>
                <button class="vam ml5" @click="open1(com.id)">回复</button>
                <button
                  class="vam ml5"
                  v-if="userId == com.user"
                  @click="removeCommunityTextOneComment(com.id)"
                >
                  删除
                </button>
                <br />
                <span style="padding-left: 40px">{{ com.text }}</span>

                <ol class="lh-menu-ol" style="display: block">
                  <li
                    class="lh-menu-second ml30"
                    v-for="coc in com.children"
                    :key="coc.id"
                  >
                    <img
                      style="border-radius: 50%"
                      :src="coc.avatar"
                      width="30"
                      height="30"
                      class="vam picImg"
                      alt
                    />
                    <span class="vam ml5">{{ coc.nickname }}</span>

                    <span class="vam ml5">{{ coc.gmtModified }}</span>
                    <button
                      class="vam ml5"
                      v-if="userId == coc.user"
                      @click="removeCommunityTextTwoComment(coc.id)"
                    >
                      删除
                    </button>
                    <br />

                    <span style="padding-left: 40px"><span style="color:#529ee9">@{{ com.nickname }}:</span>&nbsp;&nbsp;{{ coc.text }}</span>
                  </li>
                </ol>
              </li>
            </ul>
          </menu>
        </div>
      </section>
    </el-drawer>
  </header>
</template>
<script>
import community from "@/api/community/community";

export default {
  data() {
    return {
      confirmID: "",
      communityTextComment1: [],
      communityText1: [],
      communityId: "",
      drawer1: false,
      direction1: "ttb",
      communityText: {
        title: "",
        text: "",
        communityId: "",
      },
      userId: "",
      commentid: "", // 文章号
      communityTextComment: {
        text: "",
        parentId: "",
        communityId: "",
        communityTextId: "",
      },
      drawer: false,
      direction: "rtl",
    };
  },

  created() {
    // 显示文章
    this.communityId = this.$route.params.id;
    // 发布文章
    this.communityText.communityId = this.$route.params.id;

    this.getCommunityTextByCommunityId();

    this.getUserId();
  },

  methods: {
    // 抽屉
    handleClose(done) {
      done();
    },

    // 获取文章
    getCommunityTextByCommunityId() {
      community
        .getCommunityTextByCommunityId(this.communityId)
        .then((response) => {
          this.communityText1 = response.data.data.list;
         // console.log(this.communityText1);
        });
    },
    // 弹框
    handleClose1(done) { 
          done();
    },
    // 提交发布
    onSubmit() {
      community.saveCommunityText(this.communityText).then((response) => {
        this.communityText = {
          title: "",
          text: "",
          communityId: this.$route.params.id,
        };
        if (response.data.data.code == 20001) {
          this.$message({
            type: "error",
            message: response.data.data.message,
          });
        } else {
          this.$message({
            type: "success",
            message: "发布成功",
          });
          this.getCommunityTextByCommunityId();
          this.drawer1=false
        }

        // 刷新页面
        // window.location.reload();
      });
    },
    // 重置
    reset() {
      this.communityText = {
        title: "",
        text: "",
        communityId: this.$route.params.id,
      };
    },
    // 获取当前用户，判断是否具有某篇文章的删除权力
    getUserId() {
      community.getUserId().then((response) => {
        this.userId = response.data.data.userId;
      });
    },
    // 删除某篇文章
    removeCommunityText(id) {
      this.$confirm("此操作将永久删除该文章以及所有相关数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        community.removeCommunityText(id).then(() => {
          // window.location.reload();
          this.getCommunityTextByCommunityId();
          this.$message({
            type:'success',
            message:'删除成功'
          })
        });
      });
    },
    //评论发表
    open(id) {
      this.$prompt("发条友善的评论见证当下吧", "评论窗口", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false, //点击遮罩框可以推出
      })
        .then(({ value }) => {
          // 社区评论
          this.communityTextComment.communityId = this.$route.params.id;
          // console.log("社区号" + this.communityTextComment.communityId);
          this.communityTextComment.communityTextId = id;
          // console.log("文章号" + this.communityTextComment.communityTextId);
          this.communityTextComment.text = value;
          //console.log("评论内容" + this.communityTextComment.text);
          community
            .saveCommunityTextComment(this.communityTextComment)
            .then((request) => {
              // location.reload();
              this.$message({
                type: "success",
                message: "评论成功",
              });
              this.showComment(id);
              
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消评论",
          });
        });
    },

    // 获取评论
    showComment(id) {
      this.commentid = id;
      this.drawer = true;
      community.getCommunityCommentByTextId(id).then((response) => {
        this.communityTextComment1 = response.data.data.list;
       // console.log(this.communityTextComment1);
      });
    },

    //评论回复2
    open1(id) {
      this.$prompt("发条友善的评论见证当下吧", "评论窗口", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false, //点击遮罩框可以推出
      })
        .then(({ value }) => {
          // 社区评论
          this.communityTextComment.communityId = this.$route.params.id;
         // console.log("社区号" + this.communityTextComment.communityId);
          this.communityTextComment.parentId = id;
         // console.log("夫评论" + this.communityTextComment.communityTextId);
          this.communityTextComment.text = value;
         // console.log("评论内容" + this.communityTextComment.text);
          this.communityTextComment.communityTextId = this.commentid;
          //console.log("文章号" + this.commentid);
          community
            .saveCommunityTextComment(this.communityTextComment)
            .then((request) => {
              // location.reload();
              this.$message({
                type: "success",
                message: "评论成功",
              });
              this.showComment(this.communityTextComment.communityTextId)
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消评论",
          });
        });
    },

    // 删除某个一级评论
    removeCommunityTextOneComment(id) {
      this.$confirm("确定要删除该评论吗, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        community.removeCommunityTextOneComment(id).then(() => {
          this.showComment(this.commentid)
        });
      });
    },
    // 删除某个二级评论
    removeCommunityTextTwoComment(id) {
      this.$confirm("确定要删除该评论吗, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        community.removeCommunityTextTwoComment(id).then(() => {
          // location.reload();
          this.$message({
            type: "success",
            message: "删除成功",
          });
           this.showComment(this.commentid)
        });
      });
    },
  },
};
</script>
