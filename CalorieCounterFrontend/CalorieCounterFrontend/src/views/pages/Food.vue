<template>
  <v-app>
    <v-main class="bg-grey-darken-4">
      <v-container
        style="height: 50vh; max-height: 100vh"
        class="align-center d-flex"
      >
        <v-row justify="center">
          <v-col cols="12" lg="6">
            <v-card elevation="10">
              <v-card-item>
                <v-card-title class="text-center">Calorie Counter</v-card-title>
              </v-card-item>
              <v-card-text>
                <v-form @submit.prevent="submitData">
                  <v-text-field v-model="food" label="Food"></v-text-field>
                  <v-text-field
                    v-model="calories"
                    label="Calories"
                  ></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn @click="submitData" block variant="outlined"
                  >Submit</v-btn
                >
              </v-card-actions>
            </v-card>
            <v-snackbar v-model="snackbar">
              {{ response }}
              <template v-slot:actions>
                <v-btn color="purple" variant="text" @click="snackbar = false">
                  Close
                </v-btn>
              </template>
            </v-snackbar>
            <p>Your current calorie intake is: {{ currentCalorieIntake }}</p>
          </v-col>
        </v-row>
      </v-container>
      <v-container>
        <v-row justify="center">
          <v-col cols="12" lg="6">
            <v-card>
              <v-card-item>
                <v-card-title class="text-center">Search bar</v-card-title>
              </v-card-item>
              <v-card-text>
                <v-form @submit.prevent="searchFoodButton">
                  <v-text-field v-model="search" label="Search"></v-text-field>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn @click="searchFoodButton" block variant="outlined">Search</v-btn>
              </v-card-actions>
            </v-card>
            <v-list>
              <v-list-item v-for="(result, index) in searchResults" :key="index" @click="selectResult(result)">
                <v-list-item-title>{{ result.name}}</v-list-item-title>
              </v-list-item>
            </v-list>
            <v-snackbar v-model="snackbar">
              {{ searchResponse }}
              <template v-slot:actions>
                <v-btn color="purple" variant="text" @click="snackbar = false">
                  Close
                </v-btn>
              </template>
            </v-snackbar>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import foodServices from "../../services/food.service";
import userServices from "../../services/user.service";

export default {
  data() {
    return {
      search: "apple",
      food: "Test food",
      calories: "100",
      response: "",
      searchResponse: "",
      snackbar: false,
      currentCalorieIntake: "",
      searchResults: [],
    };
  },
  mounted() {
    userServices
      .getCalorieIntake()
      .then((serverResponse) => {
        this.currentCalorieIntake = serverResponse;
      })
      .catch((error) => {
        this.currentCalorieIntake = error;
      });
  },
  methods: {
    submitData() {
      foodServices
        .submitCalories(this.food, this.calories)
        .then((serverResponse) => {
          this.response = serverResponse;
          this.snackbar = true;
          foodServices.foodCaloriesCalc();
          location.reload();
        })
        .catch((error) => {
          this.response = error;
          this.snackbar = true;
        });
    },
    searchFoodButton() {
      foodServices
        .searchFood(this.search)
        .then((serverResponse) => {
          const parsedResponse = JSON.parse(serverResponse);
          const foods = parsedResponse;
          this.searchResults = foods;
          console.log("it works?");
          console.log(serverResponse);
          this.snackbar = true;
        })
        .catch((error) => {
          this.searchResponse = error;
          console.log(error);
          this.searchResponse = error;
          this.snackbar = true;
          //console.log(serverResponse);
        });
    },
    selectResult(result) {
      this.food = result.name;
      this.search = result.name;
      this.searchResults = [];
    },
  },
};
</script>
