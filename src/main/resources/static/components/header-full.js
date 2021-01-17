Vue.component('header-full', {
	template: `
		<div class="header-full" :class="{bordered: bordered}">
			<min-width-container class="header-full-bar">
				<div class="header-full-bar-content">
					<div class="text-general-1 text-4">
					    <span style="padding-left:40px">欢迎访问学者科技配件商城</span><span class="m-l-20 m-r-16">[访问官网]</span>
                        <span class="text-important-very" v-if="user.id === undefined || user.role === 1">
                            <a class="text-important-very" href="./login.html">登录</a>
                            <span> | </span>
                            <a class="text-important-very" href="./register.html">免费注册</a>
                        </span>
                        <span class="text-important-very" v-else>
                            <span class="text-important-very">{{user.account}}</span>
                            <span> | </span>
                            <span class="text-important-very pointer" @click="logout">登出</span>
                        </span>
					</div>
					<div class="text-general-1 text-4"><span @click="go_order" class="pointer">我的商城</span> <span class="el-icon-shopping-cart-full text-3 m-l-20"></span><span class="m-r-20 pointer" @click="go_cart">购物车[{{cart_num}}]</span> 我的关注</div>
				</div>
			</min-width-container>
			<min-width-container>
				<div class="header-full-content">
					<div class="pointer" @click="go_home"><img src="/images/logo.png" /></div>
					<div class="header-full-content-search">
						<div>
							<div class="header-full-content-search-input"><input v-model="keyword_data" type="text" placeholder="请输入搜索关键词"/> </div>
							<div class="header-full-content-search-button center text-weak-3 pointer" @click="search">搜索</div>
						</div>
						<div class="text-general-2 text-4 m-t-4"><span class="m-r-20">热销: </span><span>闸板泵</span></div>
					</div>
					<div class="header-full-content-contact center text-general-1"><div>4000-XXX-XXX</div></div>
				</div>
			</min-width-container>
		</div>
	`,
	props:{
		bordered: {type: Boolean, default: false},
		keyword: {type: String, default: '闸板泵'},
		on_search: {type: Function, default: null}
	},
	data(){
	    return {
	        user: {},
	        cart_num: 0,
	        keyword_data: '闸板泵'
	    };
	},
	created(){
	    this.keyword_data = this.keyword;
	    const user = JSON.parse(sessionStorage.getItem("user"));
	    if(user != null){
            this.user = user;
            //获取购物车中商品数量
            axios.post("/cart/getcartcount.do").then(response=>{
                if(response.data.status === 0){
                    this.cart_num = response.data.data;
                }else{
                    console.log(response.data.message);
                }
            }).catch(err=>{
                console.log(err);
            });
	    }
	},
	methods: {
		go_home(){
			window.location.href = "/";
		},
		search(){
		    if(this.on_search === null){
			    window.location.href = "/commodity-search.html?" + this.keyword_data;
		    }else{
		        this.on_search(this.keyword_data);
		    }
		},
		go_cart(){
		    if(this.can_go_user_center()){
			    window.location.href = "/cart.html";
		    }
		},
		go_order(){
		    if(this.can_go_user_center()){
			    window.location.href = "/order.html";
		    }
		},
		can_go_user_center(){
		    return this.user.id !== undefined && this.user.role !== 1;
		},
		logout(){
		    sessionStorage.removeItem("user");
		    this.user = {};
		    window.location.href = "/login.html";
		}
	},
})