<template>
    <v-app>
        <v-main class="bg-grey-darken-4">
            <div class="bg-image">
                <v-container style="height: 70vh; max-height: 100vh" class="align-center d-flex">
                    <v-row justify="center">
                        <v-col cols="12" lg="6">
                            <v-card elevation="10">
                                <v-card-item>
                                    <v-card-title class="text-center">{{
                                        foodName
                                    }}</v-card-title>
                                </v-card-item>
                                <v-card-text>
                                    <v-form>
                                        <v-text-field v-model="NOS" label="Number of Servings"
                                            @input="foodDataFunc"></v-text-field>
                                        <v-select v-model="servingSize" label="Serving Size" :items="measurements"
                                            item-title="desc" item-value="level"></v-select>
                                    </v-form>
                                </v-card-text>
                                <v-card-actions>
                                    <v-btn @click="submitData" block variant="outlined btn btn-primary">Add
                                        food</v-btn>
                                </v-card-actions>
                                <v-card-item>
                                    <v-card-text class="text-center">
                                        <!-- <v-card-text class="text-center">{{ foodInfo }}</v-card-text> -->
                                        <template v-if="foodInfo.carbs ||
                                                foodInfo.protein ||
                                                foodInfo.fat ||
                                                foodInfo.calories
                                                ">
                                            <table class="table table-striped">
                                                <tbody>
                                                    <tr>
                                                        <td scope="row" class="bg-primary text-white">
                                                            Carbs
                                                        </td>
                                                        <td>{{ foodInfo.carbs }}g</td>
                                                    </tr>
                                                    <tr>
                                                        <td scope="row" class="bg-danger text-white protein-cell">
                                                            Protein
                                                        </td>
                                                        <td>{{ foodInfo.protein }}g</td>
                                                    </tr>
                                                    <tr>
                                                        <td scope="row" class="bg-success text-white fat-cell">
                                                            Fat
                                                        </td>
                                                        <td>{{ foodInfo.fat }}g</td>
                                                    </tr>
                                                    <tr>
                                                        <td scope="row" class="bg-warning text-white calories-cell">
                                                            Calories
                                                        </td>
                                                        <td>{{ foodInfo.calories }}</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </template>
                                    </v-card-text>
                                </v-card-item>
                                <v-card-item>
                                    {{ AddFoodresponse }}
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
import foodServices from "../../services/food.service";
// import userServices from "../../services/user.service";

export default {
    data() {
        return {
            measurements: [
                { level: "ml", desc: "1.0 Milliliter" },
                { level: "gram", desc: "1.0 Gram" },
                { level: "ounce", desc: "1.0 Ounce" },
                { level: "pound", desc: "1.0 Pound" },
                { level: "kilogram", desc: "1.0 Kilogram" },
                { level: "cup", desc: "Cup" },
                { level: "serving", desc: "Serving" },
            ],
            foodInfo: {
                carbs: null,
                protein: null,
                fat: null,
                calories: null,
            },
            servingSize: "gram",
            NOS: "",
            foodName: this.$route.query.food,
            foodId: this.$route.query.foodId,
            AddFoodresponse: ""
        };
    },
    methods: {
        async foodDataFunc() {
            console.log(this.servingSize);
            const foodData = {
                foodId: this.foodId,
                measurements: this.servingSize,
                NOS: this.NOS,
            };
            try {
                const response = await foodServices.foodInformation(foodData);
                console.log(response);
                this.foodInfo = response;
            } catch (error) {
                // this.foodInfo = 'Error: Food information not found.';
                (this.foodInfo = {
                    carbs: 0,
                    protein: 0,
                    fat: 0,
                    calories: 0,
                }),
                    console.log(error);
            }
        },
        submitData() {
            foodServices
                .submitCalories(this.foodName, this.foodInfo.calories)
                .then((serverResponse) => {
                    this.AddFoodresponse = serverResponse;
                    foodServices.foodCaloriesCalc();
                    this.$router.push('/food');
                })
                .catch((error) => {
                    this.AddFoodresponse = error;
                });
        }
    },
    watch: {
        servingSize: function () {
            this.foodDataFunc();
        },
    },
};
</script>
<style scoped>
.bg-image {
    background-image: url("src/images/image2.jpg");
    background-size: cover;
    background-position: center center;
    background-repeat: no-repeat;
    background-attachment: fixed;
    /* filter: brightness(1.2) contrast(1.2); */
    height: 100vh;
    width: 100vw;
    overflow: hidden;
    z-index: 1;
    overflow: auto;
}

@media only screen and (max-width: 1068px) {
    .bg-image {
        background-size: contain;
        background-position: center top;
        z-index: 1;
        overflow: auto;
    }
}

@media only screen and (max-width: 1076px) {
    .bg-image {
        background-size: cover;
        z-index: 1;
        overflow: auto;
    }
}
</style>
