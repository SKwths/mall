Vue.component('header-full-app', {
    template: `
		<div class="header-full" :class="{bordered: bordered}">
		
			<min-width-container>
				<div class="header-full-content">
					<div class="header-full-content-search">
						<div>
							<div class="header-full-content-search-input"><input v-model="keyword_data" type="text" placeholder="请输入搜索关键词"/> </div>
							<div class="header-full-content-search-button center text-weak-3 pointer" @click="search">搜索</div>
						</div>
					</div>
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
    }
})