Vue.component('user-frame-app', {
    template: `
		<min-width-container>
			<div class="user-frame-app">
				<div class="user-frame-app-right">
					<slot></slot>
				</div>
			</div>
		</min-width-container>
	`,
})