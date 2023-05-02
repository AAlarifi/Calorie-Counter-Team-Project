<template>
    <div class="container">
        <h1 class="text-center m-4">Login</h1>
        <form class="text-center custom-bg rounded rounded-5 p-3 text-white" @submit.prevent="login"
            v-on:submit="login">
            <div class="form-group">
                <label class="form-label" for="email">Email: </label>
                <input class="form-control text-center" type="email" name="email" v-model="email"
                    placeholder="your email address..." />
                <div v-show="submitted && !email">Email is required</div>
            </div>


            <div class="form-group">
                <label class="form-label" for="password">Password: </label>
                <input class="form-control text-center" type="password" name="password" v-model="password"
                    placeholder="your password..." />

                <div v-show="submitted && !password">Password is required</div>
            </div>


            <button type="submit" class="btn btn-light mt-2">Login</button>
            <div v-if="error">{{ error }}</div>

        </form>
    </div>
</template>

<script>
// import { userService } from "../../services/user.service";
// import EmailValidator from "email-validator";

export default {
    data() {
        return {
            email: "admin@admin.com",
            password: "Admin123!",
            submitted: false,
            loading: true,
            error: ""
        }
    },
    methods: {
        login() {
            this.submitted = true
            if (!this.email || !this.password) {
                return
            }

            if (!EmailValidator.validate(this.email)) {
                this.error = "Email must be a valid email."
                return
            }
            const password_pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,30}$/
            if (!password_pattern.test(this.password)) {
                this.error = "Password must be atleast 8 - 30 characters long with at least one lowercase letter, one upercase letter, one special character and one number"
                return
            }

            userService.login(this.email, this.password)
                .then(result => {
                    console.log('Auth - go to dashboard')
                    this.$router.push('/dashboard')
                    location.reload()
                })
                .catch(error => {
                    this.error = error
                    this.loading = false
                })
        }
    }
}
</script>

<style scoped>
.custom-bg {
    background-color: #a1a1a1;
}
</style>