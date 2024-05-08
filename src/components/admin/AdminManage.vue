<template>
  <div>
    <div style="margin-top: 15px; margin-left: 15px">
      <form class="form" style="display: inline-block">
        <label for="search">
          <input
            required=""
            autocomplete="off"
            placeholder="请输入姓名"
            id="search"
            type="text"
            v-model="name"
            style="width: 250px; margin-right: 15px"
            @keyup="loadPost"
          />
          <div class="icon">
            <svg
              stroke-width="2"
              stroke="currentColor"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              class="swap-on"
            >
              <path
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                stroke-linejoin="round"
                stroke-linecap="round"
              ></path>
            </svg>
            <svg
              stroke-width="2"
              stroke="currentColor"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              class="swap-off"
            >
              <path
                d="M10 19l-7-7m0 0l7-7m-7 7h18"
                stroke-linejoin="round"
                stroke-linecap="round"
              ></path>
            </svg>
          </div>
          <button type="reset" class="close-btn">
            <svg
              viewBox="0 0 20 20"
              class="h-5 w-5"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                clip-rule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                fill-rule="evenodd"
              ></path>
            </svg>
          </button>
        </label>
      </form>
      <div
        style="margin-top: 15px; margin-bottom: 10px; display: inline"
        class="input"
      >
        <!-- <div style="float: left"> -->
        <!-- <el-input
          v-model="name"
          size="medium"
          placeholder="请输入姓名"
          suffix-icon="el-icon-search"
          style="width: 250px; margin-right: 15px"
          @keyup.enter.native="loadPost"
        ></el-input> -->
        <!-- </div> -->

        <el-select
          style="margin-left: 15px; width: 250px; margin-right: 20px"
          size="small"
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

        <!-- <el-button
        size="medium"
        style="margin-right: 15px"
        @click="loadPost"
        round
        type="primary"
        plain
        >查询</el-button
      > -->

        <el-button
          icon="el-icon-plus"
          class="button2 button3"
          size="medium"
          type="primary"
          style="
            margin-left: 10px;
            margin-bottom: 10px;
            display: inline-block;
            margin-right: 10px;
          "
          @click="add"
          round
          >新增</el-button
        >
        <el-button
          icon="el-icon-refresh-right"
          class="button2 button4"
          size="medium"
          @click="resetParam"
          round
          style="margin-right: 10px"
          >重置</el-button
        >
      </div>
    </div>

    <el-table
      style="
        margin-left: 10px;
        margin-top: 15px;
        margin-bottom: 20px;
        width: 98%;
        border-radius: 20px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      "
      :data="tableData"
      :header-cell-style="{ background: '#fff', color: '#555555' }"
      border
    >
      <el-table-column
        prop="id"
        label="账户编号"
        width="110"
        style="text-align: center"
      >
      </el-table-column>
      <el-table-column prop="no" label="账号" width="100"> </el-table-column>
      <el-table-column prop="name" label="姓名" width="110"> </el-table-column>
      <el-table-column prop="email" label="邮箱" width="180"> </el-table-column>
      <el-table-column prop="age" label="年龄" width="100"> </el-table-column>
      <el-table-column prop="sex" label="性别" width="100">
        <template slot-scope="scope">
          <div
            :type="scope.row.sex === 1 ? 'primary' : 'success'"
            disable-transitions
          >
            {{ scope.row.sex === 1 ? "男" : "女" }}
          </div></template
        >
      </el-table-column>
      <el-table-column prop="roleId" label="身份" width="130">
        <template slot-scope="scope">
          <el-tag
            :type="
              scope.row.roleId === 0
                ? 'danger'
                : scope.row.roleId === 1
                ? 'danger'
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
      <el-table-column prop="phone" label="电话" width="160px">
      </el-table-column>
      <el-table-column prop="operate" label="操作" class="cell">
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
            title="确定删除此管理员所有信息吗？"
            @confirm="del(scope.row.id)"
            type="text"
          >
            <el-button
              class="del"
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
      style="margin-top: 10px; margin-left: 15px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <el-dialog
      title="新增管理员"
      :visible.sync="dialogVisible"
      width="36%"
      center
    >
      <el-form
        ref="form"
        :model="form"
        label-width="70px"
        :rules="rules"
        style="transform: translateX(25px)"
      >
        <el-form-item label="账号" prop="no">
          <el-col :span="18"
            ><el-input v-model="form.no" size="small"></el-input
          ></el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-col :span="18">
            <el-input v-model="form.email" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="18">
            <el-input v-model="form.password" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="18">
            <el-input v-model="form.age" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="1" style="margin-right: 35px">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="18">
            <el-input v-model="form.phone" size="small"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="dialogVisible = false"
          style="transform: translateY(-30px); margin-right: 10px"
          size="medium"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="save"
          style="transform: translateY(-30px)"
          size="medium"
          >提 交</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="编辑管理员"
      :visible.sync="dialogVisible2"
      width="36%"
      center
    >
      <el-form
        ref="form"
        :model="form"
        label-width="70px"
        :rules="rules"
        style="transform: translateX(25px)"
      >
        <el-form-item label="账号" prop="no">
          <el-col :span="18"
            ><el-input v-model="form.no" size="small"></el-input
          ></el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="18">
            <el-input v-model="form.password" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-col :span="18">
            <el-input v-model="form.email" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="18">
            <el-input v-model="form.phone" size="small"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="dialogVisible2 = false"
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
  name: "AdminManage",
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
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: "",
      sex: "",
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
      dialogVisible2: false,
      form: {
        id: "",
        no: "",
        name: "",
        password: "",
        age: "",
        phone: "",
        sex: "0",
        roleId: "1",
        email: "",
      },
      rules: {
        no: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            min: 3,
            max: 10,
            message: "长度在 3 到 10 个字符",
            trigger: "blur",
          },
          { validator: checkDuplicate, trigger: "blur" },
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        password: [
          { required: false, message: "请输入密码", trigger: "blur" },
          {
            min: 3,
            max: 18,
            message: "长度在 3 到 18 个字符",
            trigger: "blur",
          },
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
      this.dialogVisible2 = true;
      this.$nextTick(() => {
        this.form.id = row.id;
        this.form.no = row.no;
        this.form.name = row.name;
        this.form.password = "";
        this.form.age = row.age + "";
        this.form.sex = row.sex = "";
        this.form.phone = row.phone;
        this.form.roleId = row.roleId;
        this.form.email = row.email;
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

            this.loadPost();
            this.resetForm();
            this.dialogVisible2 = false;
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
        .post(this.$httpUrl + "/user/Pagequery", {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            sex: this.sex,
            roleId: "1",
            email: this.email,
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
.el-table {
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
.form {
  --input-bg: #fff;
  /*  background of input */
  --padding: 1.5em;
  --rotate: 80deg;
  /*  rotation degree of input*/
  --gap: 2em;
  /*  gap of items in input */
  --icon-change-color: #15a986;
  /*  when rotating changed icon color */
  --height: 32px;
  /*  height */
  width: 233px;
  padding-inline-end: 1em;
  /*  change this for padding in the end of input */
  background: var(--input-bg);
  position: relative;
  border-radius: 4px;
}

.form label {
  display: flex;
  align-items: center;
  width: 100%;
  height: var(--height);
}

.form input {
  width: 100%;
  padding-inline-start: calc(var(--padding) + var(--gap));
  outline: none;
  background: none;
  border: 0;
}
/* style for both icons -- search,close */
.form svg {
  /* display: block; */
  color: #111;
  transition: 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: absolute;
  height: 15px;
}
/* search icon */
.icon {
  position: absolute;
  left: var(--padding);
  transition: 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  justify-content: center;
  align-items: center;
}
/* arrow-icon*/
.swap-off {
  transform: rotate(-80deg);
  opacity: 0;
  visibility: hidden;
}
/* close button */
.close-btn {
  /* removing default bg of button */
  background: none;
  border: none;
  right: calc(var(--padding) - var(--gap));
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #111;
  padding: 0.1em;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  transition: 0.3s;
  opacity: 0;
  transform: scale(0);
  visibility: hidden;
}

.form input:focus ~ .icon {
  transform: rotate(var(--rotate)) scale(1.3);
}

.form input:focus ~ .icon .swap-off {
  opacity: 1;
  transform: rotate(-80deg);
  visibility: visible;
  color: var(--icon-change-color);
}

.form input:focus ~ .icon .swap-on {
  opacity: 0;
  visibility: visible;
}

.form input:valid ~ .icon {
  transform: scale(1.3) rotate(var(--rotate));
}

.form input:valid ~ .icon .swap-off {
  opacity: 1;
  visibility: visible;
  color: var(--icon-change-color);
}

.form input:valid ~ .icon .swap-on {
  opacity: 0;
  visibility: visible;
}

.form input:valid ~ .close-btn {
  opacity: 1;
  visibility: visible;
  transform: scale(1);
  transition: 0s;
}

.button2 {
  display: inline-block;
  transition: all 0.2s ease-in;
  position: relative;
  overflow: hidden;
  z-index: 1;
  color: #090909;
  padding: 0.7em 1.2em;
  cursor: pointer;
  font-size: 13px;
  border-radius: 0.5em;
  background: #d7f2df;
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
  background-color: #cbf9e4;
}
</style>
