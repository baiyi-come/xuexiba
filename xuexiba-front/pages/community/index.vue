<template>
  <div id="aCoursesList" class="bg-fa of">
    <el-form
      :inline="true"
      style="padding-left: 100px; padding-top: 10px"
      class="demo-form-inline"
    >
      <el-form-item>
        <el-input
          placeholder="请输入社区名"
          v-model="CommunityName"
          @kepup.enter.native="getCommunityByName()"
        />
      </el-form-item>

      <el-button
        type="primary"
        icon="el-icon-search"
        @click="getCommunityByName()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
      <el-button
        type="text"
        style="float: right; padding-right: 50px"
        @click="dialog = true"
        >我也要建立自己的社区</el-button
      >
      <!-- <el-button type="text" style="float:right;padding-right:50px" @click="tOpen()">我也要建立自己的社区</el-button> -->
      <!-- 嵌套开始 -->
      <el-drawer
        :visible.sync="dialog"
        direction="ltr"
        custom-class="demo-drawer"
        ref="drawer"
      >
        <div class="demo-drawer__content">
          <el-form label-width="80px">
            <span>社区名称</span>
            <el-input v-model="comment.title" />
            <!--社区头像 -->
            <span>社区头像</span>
            <div style="padding: 100px">
              <el-upload
                class="avatar-uploader"
                action="http://127.0.0.1:81/eduoss/fileoss"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
              >
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="点击上传或更改头像"
                  placement="top"
                >
                  <img
                    v-if="comment.avator"
                    :src="comment.avator"
                    style="height: 150px; width: 150px"
                    class="avatar"
                  />
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-tooltip>
              </el-upload>
            </div>
          </el-form>
          <div class="demo-drawer__footer">
            <el-button @click="cancelForm">取 消</el-button>
            <el-button type="primary" @click="saveComment" :loading="loading">{{
              loading ? "提交中 ..." : "确 定"
            }}</el-button>
          </div>
        </div>
      </el-drawer>

      <!-- 嵌套结束 -->
    </el-form>
    <div style="padding:50px">
      <div v-if="this.total > 0" style="padding-bottom:20px">
        <el-pagination
          background
          :page-size="pageSize"
          :current-page="pageNum"
          style="text-align: center"
          layout="total,prev, pager, next,jumper"
          prev-text="上一页"
          next-text="下一页"
          :total="total"
          @current-change="getCommunity"
        >
        </el-pagination>
      </div>
      <!-- /无数据提示 开始-->
      <section class="no-data-wrap" v-if="this.total == 0">
        <em class="icon30 no-data-ico">&nbsp;</em>
        <span class="c-666 fsize14 ml10 vam"
          >还没有社区呢，快去创建一个吧！</span
        >
      </section>
      <!-- /无数据提示 结束-->
      <aside v-if="this.total > 0 || this.total == -1">
        <aside class="fl col-3" v-for="c in this.communityList" :key="c.id" style="padding-bottom:10px">
          <div class="i-box">
            <el-tooltip
              class="item"
              effect="dark"
              content="点击进入社区"
              placement="top-start"
              ><section class="c-infor-tabTitle c-tab-title">
                <a title :href="'/community/' + c.id"
                  >{{ c.title }}</a
                >   
              </section>
            </el-tooltip>
 
            <section class="stud-act-list">
              <ul style="height: auto">
                <li>
                  <div class="u-face">
                    <a :href="'/community/' + c.id">
                      <img :src="c.avator" width="50" height="50" alt />
                    </a>
                  </div>
                  <section class="hLh30 txtOf">
                    <a class="c-333 fsize16 fl" @click="user(c.userId)"
                      >点击查看创建者</a
                    >
                    <a class="c-333 fsize16 fl" v-if="c.userId==userId" @click="removeCommentById(c.id)" style="float:right;color:#93c7fd"
                      >解散</a>
                  </section>
                  <section class="hLh30 txtOf">
                    <span style="font-size:1px" class="c-333 fsize16 fl"
                      >创建时间：{{ c.gmtCreate }}</span
                    >
                  </section>
                  <!-- <section class="hLh20 txtOf">
                    <span class="c-999">头衔</span>
                  </section> -->
                </li>
              </ul>
            </section>
          </div>
        </aside>
      </aside>
    </div>
  </div>
</template>

<script>
import community from "@/api/community/community";
export default {
  data() {
    return {
      comment: {
        title: "",
        avator:
          "http://aliyun_id_photo_bucket.oss.aliyuncs.com/default_handsome.jpg?imageView2/1/w/80/h/80",
      },
      pageNum: 1,
      pageSize: 12,
      total: 0,
      table: false,
      dialog: false,
      loading: false,
      CommunityName: "",
      url: "http://aliyun_id_photo_bucket.oss.aliyuncs.com/default_handsome.jpg?imageView2/1/w/80/h/80",
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
      communityList: [],
      formLabelWidth: "80px",
      timer: null,
      // communityByTwo:[]
      userId:""
    };
  },

  created() {
    this.getCommunity();
    // this.getCommunityLimitTwo()
    this.getUserId()
  },

  methods: {
    cancelForm() {
      this.loading = false;
      this.dialog = false;
      clearTimeout(this.timer);
    },

    handleAvatarSuccess(res, file) {
      this.comment.avator = res.data.url;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },

    // 保存社区
    saveComment() {
      community
        .saveComment(this.comment)
        .then((res) => {
          this.comment = {
            title: "",
            avator:
              "http://aliyun_id_photo_bucket.oss.aliyuncs.com/default_handsome.jpg?imageView2/1/w/80/h/80",
          };
          if (res.data.code == 20001) {
            this.$message({
              type: "error",
              message: res.data.message,
            });
          } else {
            this.$message({
              type:'success',
              message:'创建成功'
            })
             this.getCommunity();
             this.cancelForm()
          }
        })
        .catch((res) => {});
    },

    // 查询社区
    getCommunity(page = 1) {
      this.pageNum = page;
      community.getCommunity(this.pageNum, this.pageSize).then((response) => {
        this.communityList = response.data.data.records;
        this.total = response.data.data.total;
      });
    },

    // 搜索社区
    getCommunityByName() {
      community.getCommunityByName(this.CommunityName).then((response) => {
        this.communityList = response.data.data.list;
        if (this.communityList == "") {
          this.$message({
            type: "warning",
            message: "没有相应的社区",
          });
        }
        this.total = -1;
      });
    },
    resetData() {
      this.CommunityName = "";
      this.getCommunity();
    },

// 删除社区
    removeCommentById(id){
        this.$confirm("此操作将永久删除该社区且无法恢复, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
         community.removeCommunity(id).then(()=>{
        // location.reload()
        this.$message({
          type:'success',
          message:'解散成功'
        })
        this.getCommunity();
        })
      });
    },
    user(userId) {
      community.getUser(userId).then((response) => {
        this.$message({
          type: "warning",
          message: "创建者为：" + response.data.data.user.nickname,
        });
      });
    },
    // 获取当前用户id
    getUserId(){
      community.getUserId().then(response=>{
          this.userId=response.data.data.userId
      })
    }
  },
};
</script>
