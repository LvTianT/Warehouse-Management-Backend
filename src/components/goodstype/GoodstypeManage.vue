<template>
  <div>
    <div style="margin-bottom: 5px">
      <span class="type" style="margin-left: 10px; font-size: 14px"
        >分类：</span
      >
      <el-input
        size="medium"
        v-model="name"
        placeholder="请输入"
        style="width: 200px; margin-left: 5px; margin-top: 20px"
        @keyup.enter.native="loadPost"
      ></el-input>
      <div style="float: right; margin-right: 20px; margin-top: 20px">
        <el-button
         icon="el-icon-plus"
          type="primary"
          style="margin-left: 20px"
          @click="add"
          class="button2 button3"
          >新增</el-button
        >
        <el-button
          icon="el-icon-refresh-right"
          type="success"
          style="margin-left: 15px"
          @click="resetParam"
          class="button2 button4"
          >重置</el-button
        >
      </div>
      <el-button icon="el-icon-search" style="margin-left: 20px" @click="loadPost" class="button2"
        >查询</el-button
      >
    </div>
    <el-table
      style="
        margin-left: 10px;
        margin-top: 30px;
        width: 98%;
        border-radius: 15px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      "
      :data="tableData"
      :header-cell-style="{ background: '#fff', color: '#555555' }"
      :class="table"
      border
    >
      <el-table-column prop="id" label="类别编号" width="160">
      </el-table-column>
      <el-table-column prop="name" label="分类名" width="200">
      </el-table-column>
      <el-table-column prop="remark" label="备注"> </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button
            class="edit"
            size="medium"
            icon="el-icon-edit"
            @click="mod(scope.row)"
            circle
            style="margin-right: 15px; margin-left: 5px"
          ></el-button>
          <el-popconfirm
            title="确定删除此商品吗？"
            @confirm="del(scope.row.id)"
            style="margin-left: 5px"
          >
            <el-button
              class="del"
              slot="reference"
              size="medium"
              type="primary"
              icon="el-icon-delete"
              circle
            ></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-left: 10px; margin-top: 25px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 7, 9, 10]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <el-dialog
      title="新增分类"
      :visible.sync="dialogVisible"
      width="36%"
      center
    >
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="分类名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="dialogVisible = false"
          style="transform: translateY(-30px); margin-right: 10px"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="save"
          style="transform: translateY(-30px)"
          >提 交</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="编辑分类"
      :visible.sync="centerDialogVisible"
      width="36%"
      center
    >
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="分类名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input type="textarea" v-model="form.remark"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="centerDialogVisible = false"
          style="transform: translateY(-30px); margin-right: 10px"
          size="medium"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="save"
          style="transform: translateY(-30px)"
          size="medium"
          >保 存</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "GoodstypeManage",
  data() {
    return {
      tableData: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      name: "",
      dialogVisible: false,
      centerDialogVisible: false,
      form: {
        id: "",
        name: "",
        remark: "",
      },
      rules: {
        name: [{ required: true, message: "请输入分类", trigger: "blur" }],
      },
    };
  },
  methods: {
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(id) {
      console.log(id);

      this.$axios
        .get(this.$httpUrl + "/goodstype/del?id=" + id)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "删除成功！",
              type: "success",
            });
            this.loadPost();
          } else {
            this.$message({
              message: "删除失败！",
              type: "error",
            });
          }
        });
    },
    mod(row) {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        //赋值到表单
        this.form.id = row.id;
        this.form.name = row.name;
        this.form.remark = row.remark;
      });
    },
    add() {
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
   
      });
    },
    doSave() {
      this.$axios
        .post(this.$httpUrl + "/goodstype/save", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "添加成功！",
              type: "success",
            });
            this.dialogVisible = false;
            this.loadPost();
            this.resetForm();
          } else {
            this.$message({
              message: "添加失败！",
              type: "error",
            });
          }
        });
    },
    doMod() {
      this.$axios
        .post(this.$httpUrl + "/goodstype/update", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "修改成功！",
              type: "success",
            });
            this.centerDialogVisible = false;
            this.loadPost();
            this.resetForm();
          } else {
            this.$message({
              message: "修改失败！",
              type: "error",
            });
          }
        });
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1;
      this.pageSize = val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },
    resetParam() {
      this.name = "";
    },
    loadPost() {
      this.$axios
        .post(this.$httpUrl + "/goodstype/listPage", {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
          },
        })
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert("获取数据失败");
          }
        });
    },
   
  },
  beforeMount() {
    this.loadPost();
  },
};
</script>

<style scoped>
.type {
  color: #000000;
  font-weight: 500;
}
.button2 {
  display: inline-block;
  transition: all 0.2s ease-in;
  position: relative;
  overflow: hidden;
  z-index: 1;
  color: #090909;
  padding:0.7em 1.2em;
  cursor: pointer;
  font-size: 13px;
  border-radius: 0.5em;
  background: #baf6cb99;
  border: 1px solid #fffefe;
  box-shadow: 6px 6px 12px #c5c5c5, -6px -6px 12px #ffffff;
}

.button2:active {
  color: #666;
  box-shadow: inset 4px 4px 12px #c5c5c5, inset -4px -4px 12px #ffffff;
}

.button2:before {
  content: "";
  position: absolute;
  left: 50%;
  transform: translateX(-50%) scaleY(1) scaleX(1.25);
  top: 100%;
  width: 140%;
  height: 180%;
  background-color: rgba(0, 0, 0, 0.05);
  border-radius: 50%;
  display: block;
  transition: all 0.5s 0.1s cubic-bezier(0.55, 0, 0.1, 1);
  z-index: -1;
}

.button2:after {
  content: "";
  position: absolute;
  left: 55%;
  transform: translateX(-50%) scaleY(1) scaleX(1.45);
  top: 180%;
  width: 160%;
  height: 190%;
  background-color: #95f0ea;
  border-radius: 50%;
  display: block;
  transition: all 0.5s 0.1s cubic-bezier(0.55, 0, 0.1, 1);
  z-index: -1;
}

.button2:hover {
  color: #ffffff;
  border: 1px solid #e2f5f4;
}

.button2:hover:before {
  top: -35%;
  background-color: #abcfec;
  transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
}

.button2:hover:after {
  top: -45%;
  background-color: #85b59c;
  transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
}

.button3 {
  background-color: #bcdafe;
}

.button4 {
  background-color: #c9eaf7;
}
.edit {
  border-color: transparent;
  color: #409eff;
  background: 0 0;
  margin-left: 65px;
  padding-left: 3px;
  padding-right: 0;
}
.del {
  border-color: transparent;
  color: #fa5b5b;
  background: #ffffff 0;
}
</style>
