<template>
    <v-app>
        <v-main class="bg-grey-darken-4 overflow-y-auto">
            <div class="bg-image">
                <v-container style="height: 100vh; max-height: 100vh" class="align-center d-flex">
                    <v-row justify="center">
                        <v-col cols="12" lg="6">
                            <v-card elevation="10">
                                <v-card-item>
                                    <v-card-title class="text-center btn btn-primary">Create a profile</v-card-title>
                                </v-card-item>
                                <v-card-text>
                                    <v-form @submit.prevent="createUserButton">
                                        <v-text-field v-model="firstName" label="First Name"></v-text-field>
                                        <v-alert v-if="firstNameError" class="text-center" type="error">First name is
                                            required.</v-alert>
                                        <v-text-field v-model="lastName" label="Last Name"></v-text-field>
                                        <v-alert v-if="lastNameError" class="text-center" type="error">Last name is
                                            required.</v-alert>
                                        <v-text-field v-model="email" label="Email"></v-text-field>
                                        <v-alert v-if="emailError" class="text-center" type="error">Invalid email format.
                                            Please enter a valid email address in the format example@example.com</v-alert>
                                        <v-text-field v-model="password" :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                                            :type="show1 ? 'text' : 'password'" name="input-10-1" label="Password"
                                            hint="At least 8 characters" counter
                                            @click:append="show1 = !show1"></v-text-field>
                                        <v-alert v-if="emailError" class="text-center" type="error">Invalid password format.
                                            Your password must be between 8 and 30 characters long and contain at least one
                                            lowercase letter, one uppercase letter, one digit, and one special character
                                            (@$!%*?&).</v-alert>
                                    </v-form>
                                </v-card-text>
                                <v-card-actions>
                                    <v-btn @click="createUserButton" block
                                        variant="outlined btn btn-primary">Sign-up</v-btn>
                                </v-card-actions>
                                <v-card-item>
                                    {{ createrUserResponse }}
                                </v-card-item>
                            </v-card>
                        </v-col>
                    </v-row>
                </v-container>
            </div>
        </v-main>
    </v-app>
</template>
  
<script>
import userServices from "../../services/user.service";

export default {
    data() {
        return {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            emailError: false,
            passwordError: false,
            firstNameError: false,
            lastNameError: false,
            error: "",
            createrUserResponse: "",
            show1: false,
            show2: true,
        };
    },
    methods: {
        createUserButton() {
            this.firstNameError = false;
            this.lastNameError = false;
            this.emailError = false;
            this.passwordError = false;
            if (this.firstName === "") {
                this.firstNameError = true;
            }
            if (this.lastName === "") {
                this.lastNameError = true;
            }
            if (!/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(this.email)) {
                this.emailError = true;
            }
            if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,30}$/.test(this.password)) {
                this.passwordError = true;
            }
            if (this.emailError || this.passwordError) {
                return;
            }
            userServices.createUser(this.firstName, this.lastName, this.email, this.password)
                .then(serverResponse => {
                    this.createrUserResponse = serverResponse;
                    console.log("User created successfully!");
                })
                .catch(error => {
                    this.createrUserResponse = error;
                    this.error = error;
                })
        }

    },
};
</script>
  
<style scoped>
.bg-image {
    background-image: url('src/images/image2.jpg');
    background-size: cover;
    background-position: center center;
    background-repeat: no-repeat;
    background-attachment: fixed;
    height: 100vh;
    width: 100vw;
}

@media only screen and (max-width: 3868px) {
    .bg-image {
        background-size: contain;
        background-position: center top;
    }
}

@media only screen and (max-width: 3876px) {
    .bg-image {
        background-size: cover;
    }
}
</style>
  