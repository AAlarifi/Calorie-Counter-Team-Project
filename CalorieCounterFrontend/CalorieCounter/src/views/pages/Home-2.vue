<template>
    <v-app>
        <v-main class="bg-grey-darken-4">
          <v-container style="height: 100svh; max-height: 100svh" class="align-center d-flex">
              <v-row justify="center">
                  <v-col cols="12" lg="6">
                      <v-card elevation="10">
                          <v-card-item>
                              <v-card-title class="text-center">Home Page</v-card-title>
                          </v-card-item>
                          <v-card-text>
                              <v-form  @submit.prevent="submitData();">
                                <v-select label="Select Gender" :items="['Male', 'Female']"
                                    
                                    ></v-select>  
                                  <v-text-field v-model="food" label="Weight (in kg):"></v-text-field>
                                  <v-text-field v-model="calories" label="Height (in cm):"></v-text-field>
                                  <v-text-field v-model="age" label="Age (in years):"></v-text-field>
                                  <v-select label="Select Activity Level" :items="activityLevel" item-title="desc" item-value="level"
                                    
                                    ></v-select> 
                              </v-form>
                          </v-card-text>
                          <v-card-actions>
                              <v-btn block variant="outlined">calculate AMR</v-btn>
                          </v-card-actions>
                      </v-card>
                  </v-col>
              </v-row>
          </v-container>
        </v-main>
    </v-app>
</template>
  
<script>

    export default {
      data() {
        return {
            activityLevel: [
                {level: "sedentary", desc: "Sedentary (little or no exercie)"},
                {level: "lightlyActive", desc: "Lightly active (exercise 1-3 days/week)"},
                {level: "moderatelyActive", desc: "Moderately active (exercise 3-6 days/week)"},
                {level: "active", desc: "Active (exercise 6-7 days/week)"},
                {level: "veryActive", desc: "Very active (hard exercise 6-7 days/week)"}
            ]
        }
      },
      methods: {
        submitted() {
          //TODO
        }
      }
    }
</script>