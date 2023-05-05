<template>
  <v-app style="min-height: 100%;">
    <v-main class="bg-grey-darken-4">
      <div class="bg-image" style="height: 100%; display: flex; flex-direction: column;">
        <v-container style="flex: 1 0 auto; overflow-y: auto;" class="align-center d-flex">
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
        <v-container style="flex: 1 0 auto; overflow-y: auto;" class="align-center d-flex">
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
  background-image: url('src/images/image1.jpg');
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  min-height: 100%;
  /* height: 100vh;
  width: 100vw; */
  /* z-index: 1;
  overflow: auto; */
}

.bg-grey-darken-4 {
  overflow: auto;
  z-index: 1;
}

v-container {
  margin: 1rem auto;
  padding: 1rem;
}

/* @media only screen and (max-width: 3868px) {
  .bg-image {
    background-size: contain;
    background-position: center top;
  }
}

@media only screen and (max-width: 3876px) {
  .bg-image {
    background-size: cover;
  }

} */

/* Small devices (mobile phones) */
@media only screen and (max-width: 600px) {

  /* Set the font size to 16px for small screens */
  html {
    font-size: 16px;
  }

}

/* Medium devices (tablets) */
@media only screen and (min-width: 601px) and (max-width: 960px) {

  /* Set the font size to 18px for medium screens */
  html {
    font-size: 18px;
  }
}

/* Large devices (desktops) */
@media only screen and (min-width: 961px) {

  /* Set the font size to 20px for large screens */
  html {
    font-size: 20px;
  }
}
</style>
