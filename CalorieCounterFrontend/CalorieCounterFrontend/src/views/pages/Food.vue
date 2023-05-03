<template>
  <v-app>
    <v-main class="bg-grey-darken-4">
      <div class="bg-image">
        <v-container style="height: 70vh; max-height: 70vh" class="align-center d-flex">
          <v-row justify="center">
            <v-col cols="12" lg="6">
              <v-card elevation="10">
                <v-card-item>
                  <v-card-title class="text-center">Quick Add</v-card-title>
                </v-card-item>
                <v-card-text>
                  <v-form @submit.prevent="submitData">
                    <v-text-field v-model="food" label="Food"></v-text-field>
                    <v-text-field v-model="calories" label="Calories"></v-text-field>
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-btn @click="submitData" block variant="outlined btn btn-primary">Submit</v-btn>
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
              <v-container>
                <v-row>
                  <v-col>
                    <v-card>
                      <v-card-item>
                        <v-card-text>
                          <table class="table table-striped">
                            <tbody>
                              <tr>
                                <td scope="row" class="bg-primary text-white">
                                  Your current calorie intake is:
                                </td>
                                <td>
                                  {{ currentCalorieIntake }}
                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </v-card-text>
                      </v-card-item>
                    </v-card>
                  </v-col>
                </v-row>
              </v-container>
            </v-col>
          </v-row>
        </v-container>
        <v-container style="max-height: 1vh" class="align-center d-flex">
          <v-row justify="center">
            <v-col cols="12" lg="6">
              <v-card>
                <v-card-item>
                  <v-card-title class="text-center">Search food</v-card-title>
                </v-card-item>
                <v-card-text>
                  <v-form>
                    <v-text-field v-model="search" label="Search" @input="searchFoodBar"></v-text-field>
                  </v-form>
                </v-card-text>
              </v-card>
              <v-list>
                <v-list-item v-for="(result, index) in searchResults" :key="index" @click="selectResult(result)">
                  <v-list-item-title>{{ result.name }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-col>
          </v-row>
        </v-container>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import foodServices from "../../services/food.service";
import userServices from "../../services/user.service";

export default {
  data() {
    return {
      search: "",
      food: "",
      calories: "",
      response: "",
      searchResponse: "",
      snackbar: false,
      currentCalorieIntake: "",
      searchResults: [],
      foodName: "",
      foodId: ""
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
    searchFoodBar() {
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
      foodServices.searchParser(result.name)
        .then((serverResponse) => {
          this.foodId = serverResponse;
          console.log(serverResponse);
          this.$router.push({
            path: '/Diary',
            query: {
              foodId: this.foodId,
              food: this.food
            }
          });
        })
        .catch((error) => {
          this.searchResponse = error;
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
.bg-image {
  margin: 0;
  padding: 0;
  background-image: url('src/images/image1.jpg');
  background-size: cover;
  z-index: 3;
}

/* .v-main{
  overflow-y: auto;
}

.bg-grey-darken-4{
  overflow: auto;
  z-index: 1;
}
.bg-image {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('src/images/image1.jpg');
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  filter: brightness(1.2) contrast(1.2);
  height: 100vh;
  width: 100vw;
} */

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
