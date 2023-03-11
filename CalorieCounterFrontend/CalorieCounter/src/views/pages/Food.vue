<template>
    <v-app>
        <v-main class="bg-grey-darken-4">
          <v-container style="height: 100vh; max-height: 100vh" class="align-center d-flex">
              <v-row justify="center">
                  <v-col cols="12" lg="6">
                      <v-card elevation="10">
                          <v-card-item>
                              <v-card-title class="text-center">Calorie Counter</v-card-title>
                          </v-card-item>
                          <v-card-text>
                              <v-form  @submit.prevent="submitData">
                                  <v-text-field v-model="food" label="Food"></v-text-field>
                                  <v-text-field  v-model="calories" label="Calories"></v-text-field>
                              </v-form>
                          </v-card-text>
                          <v-card-actions>
                              <v-btn @click="submitData" block variant="outlined">Submit</v-btn>
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
                  </v-col>
              </v-row>
          </v-container>
        </v-main>
    </v-app>
</template>
  
<script>

    import services from "../../services/food.service"

    export default {

      data() {
        return {
          food: "",
          calories: "",
          response: "",
          snackbar: false
        }
      },
      methods: {
        submitData() {
          services.submitCalories(this.food, this.calories)
          .then(serverResponse => {
            this.response = serverResponse;
            this.snackbar =  true;
          })
          .catch(error => {
            this.response = error;
            this.snackbar =  true;
          })
        }
      }
    }
</script>