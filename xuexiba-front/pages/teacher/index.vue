<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>

        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#"></a>
        </section>
        <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名(支持模糊查询)" />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="this.total==0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam"
              >没有相关数据，小编正在努力整理中...</span
            >
          </section>
          <!-- /无数据提示 结束-->
          <article class="i-teacher-list" v-if="this.total>0">
            <ul class="of">
              <li v-for="t in this.teacherList" :key="t.id">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :href="'/teacher/'+t.id" :title="t.name" target="_blank">
                      <img :src="t.avatar" :alt="t.name" />
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a
                      :href="'/teacher/'+t.id"
                      :title="t.name"
                      target="_blank"
                      class="fsize18 c-666"
                      >{{t.name}}</a
                    >
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999"
                      >{{t.intro}}</span
                    >
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{t.career}}</p>
                  </div>
                </section>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
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
      @current-change="getList"
    >
    </el-pagination>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>
import teacher from "@/api/teacher/teacher";
export default{
  data(){
    return {
      pageNum:1,
      pageSize:8,
      total:0,
      teacherList:[],
      teacherQuery:{
        name:""
      }
    }
  },
  created(){
      this.getList();
  },
  methods:{
    getList(page = 1) {
      this.pageNum = page;
      teacher
        .getTeacherAll(this.pageNum, this.pageSize, this.teacherQuery)
        .then((response) => {
          // 请求成功s
          this.teacherList = response.data.data.records;
          this.total = response.data.data.total;
          // console.log(response);
          // console.log(this.list);
          // console.log(this.total);
        });
    },
     resetData() {
      // 清空数据
      this.teacherQuery = {};
      // 查询所有数据
      this.getList();
    },
  }
}
</script>