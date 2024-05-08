<template>
  <div>
    <div style="margin-top: 15px; margin-bottom: 10px" class="input">
      <div style="float: left">
        <el-input
          v-model="name"
          size="medium"
          placeholder="请输入名字"
          suffix-icon="el-icon-search"
          style="width: 250px; margin-right: 15px"
          @keyup.enter.native="loadPost"
        ></el-input>
      </div>
      <el-select
        style="width: 250px; margin-right: 20px"
        size="medium"
        v-model="sex"
        filterable
        @keyup.enter.native="loadPost"
        placeholder="请选择性别"
      >
        <el-option
          v-for="item in sexs"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button
        size="medium"
        style="margin-right: 15px"
        @click="loadPost"
        round
        type="primary"
        plain
        >查询</el-button
      >
      <div style="float: right">
        <el-button
          size="medium"
          type="primary"
          style="margin-right: 1px"
          @click="add"
          round
          >新增</el-button
        >
        <el-button
          size="medium"
          @click="resetParam"
          round
          style="margin-right: 10px"
          >重置</el-button
        >
      </div>
    </div>
    <el-table
      style="margin-top: 20px; width: 99.4%; border-radius: 15px"
      :data="tableData"
      :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
      border
    >
      <el-table-column
        prop="id"
        label="ID"
        width="80"
        style="text-align: center"
      >
      </el-table-column>
      <el-table-column prop="no" label="账号" width="180"> </el-table-column>
      <el-table-column prop="name" label="姓名" width="180"> </el-table-column>
      <el-table-column prop="age" label="年龄" width="110"> </el-table-column>
      <el-table-column prop="sex" label="性别" width="110">
        <template slot-scope="scope">
          <div
            :type="scope.row.sex === 1 ? 'primary' : 'success'"
            disable-transitions
          >
            {{ scope.row.sex === 1 ? "男" : "女" }}
          </div></template
        >
      </el-table-column>
      <el-table-column prop="roleId" label="角色" width="120">
        <template slot-scope="scope">
          <el-tag
            :type="
              scope.row.roleId === 0
                ? 'danger'
                : scope.row.roleId === 1
                ? 'success'
                : 'primary'
            "
            size="medium"
            disable-transitions
            >{{
              scope.row.roleId === 0
                ? "高级管理员"
                : scope.row.roleId === 1
                ? "管理员"
                : "用户"
            }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="180"> </el-table-column>
      <el-table-column prop="operate" label="操作" class="cell">
        <template slot-scope="scope">
          <el-button
            size="medium"
            icon="el-icon-edit"
            @click="mod(scope.row)"
            circle
            style="margin-right: 15px; margin-left: 5px"
          ></el-button>
          <el-popconfirm
            title="确定删除该用户吗？"
            @confirm="del(scope.row.id)"
            type="text"
          >
            <el-button
              slot="reference"
              size="medium"
              type="primary"
              icon="el-icon-delete"
              circle
            ></el-button
          ></el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top: 30px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400"
    >
    </el-pagination>

    <el-dialog
      title="用户信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
      center
    >
      <el-form ref="form" :model="form" label-width="80px" :rules="rules">
        <el-form-item label="账号" prop="no">
          <el-col :span="20"><el-input v-model="form.no"></el-input></el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1" style="margin-right: 35px">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error("年龄输⼊过⼤"));
      } else {
        callback();
      }
    };
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$axios
        .get(this.$httpUrl + "/user/findByNo?no=" + this.form.no)
        .then((res) => res.data)
        .then((res) => {
          if (res.code != 200) {
            callback();
          } else {
            callback(new Error("账号已经存在"));
          }
        });
    };
    let checkPhone = (rule, value, callback) => {};
    return {
      tableData: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      name: '',
      sex: '',
      sexs: [
        {
          label: "男",
          value: "1",
        },
        {
          label: "女",
          value: "0",
        },
      ],

      dialogVisible: false,
      form: {
        id: "",
        no: "",
        name: "",
        password: "",
        age: "",
        phone: "",
        sex: "0",
        roleId: "1",
      },
      rules: {
        no: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" },
          { validator: checkDuplicate, trigger: "blur" },
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" },
        ],

        age: [
          { required: true, message: "请输入年龄", trigger: "blur" },
          { min: 1, max: 3, message: "长度在 1 到 3 个位", trigger: "blur" },
          {
            pattern: /^([1-9][0-9]*){1,3}$/,
            message: "年龄必须为正整数字",
            trigger: "blur",
          },
          { validator: checkAge, trigger: "blur" },
        ],
        phone: [
          { required: true, message: "手机号不能为空", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
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
        .get(this.$httpUrl + "/user/del?Id=" + id)
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
      console.log(row);
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.form.id = row.id;
        this.form.no = row.no;
        this.form.name = row.name;
        this.form.password = "";
        this.form.age = row.age + "";
        this.form.sex = row.sex = "";
        this.form.phone = row.phone;
        this.form.roleId = row.roleId;
      });
    },
    doSave() {
      this.$axios
        .post(this.$httpUrl + "/user/save", this.form)
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
          } else {
            this.$message({
              message: "添加失败！",
              type: "error",
            });
          }
        });
    },

    add() {
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
      });
    },
    doMod() {
      this.$axios
        .post(this.$httpUrl + "/user/update", this.form)
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
      this.sex = "";
    },
    loadGet() {
      this.$axios.get(this.$httpUrl + "/user/list").then((res) => {
        console.log(res.data);
      });
    },
    loadPost() {
      this.$axios
        .post(this.$httpUrl + "/user/listPageC1", {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            sex: this.sex,
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
    //this.loadGet();
    this.loadPost();
  },
};
</script>

<style scoped>
.input {
  margin: 0 auto;
}
el-table {
  box-sizing: border-box;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-break: break-all;
  line-height: 23px;
  padding-left: 20px;
  padding-right: 10px;
}

.cell {
  padding-left: 10px;
}
</style>
