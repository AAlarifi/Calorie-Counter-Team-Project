<template>
  <v-app>
    <v-main class="bg-grey-darken-4">
      <div class="bg-image">
      <v-container style="height: 110svh; max-height: 110svh" class="align-center d-flex">
        <v-row justify="center">
          <v-col cols="12" lg="6">
            <v-card elevation="10">
              <v-card-item>
                <v-card-title class="text-center btn btn-primary">Home Page</v-card-title>
              </v-card-item>
              <v-card-text>
                <v-form @submit.prevent="submitData()">
                  <v-select label="Select Gender" :items="['Male', 'Female']" v-model="gender"></v-select>
                  <v-text-field v-model="weight" label="Weight (in kg)" :rules="weightRules"></v-text-field>
                  <v-text-field v-model="height" label="Height (in cm)" :rules="heightRules"></v-text-field>
                  <v-text-field v-model="age" label="Age (in years)" :rules="ageRules"></v-text-field>
                  <v-select label="Select Activity Level" :items="activityLevel" item-title="desc" item-value="level"
                    v-model="activity"></v-select>
                    <v-select label="Select Fitness goal" :items="['Lose weight', 'Gain weight']" v-model="fitnessGoal"></v-select>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn block variant="outlined btn btn-primary" @click="calculateAMR()">calculate AMR</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </div>
    </v-main>
  </v-app>
</template>

<script>
import services from "../../services/user.service"

export default {
  data() {
    return {
      weightRules: [
        value => !value || value >= 30 && value <=200 || "Weight must be between 30kg and 200kg!"
      ],
      heightRules: [
        value => !value || value >= 50 && value <=300 || "Height must be between 50cm and 300cm!"
      ],
      ageRules: [
        value => !value || value >= 10 && value <=100 || "Age must be between 10 and 100 years old!"
      ]
      ,activityLevel: [
        { level: "sedentary", desc: "Sedentary (little or no exercie)" },
        { level: "lightlyActive", desc: "Lightly active (exercise 1-3 days/week)" },
        { level: "moderatelyActive", desc: "Moderately active (exercise 3-6 days/week)" },
        { level: "active", desc: "Active (exercise 6-7 days/week)" },
        { level: "veryActive", desc: "Very active (hard exercise 6-7 days/week)" }
      ],
      gender: "Male",
      weight: "90",
      height: "180",
      age: "26",
      activity: "lightlyActive",
      fitnessGoal: "Lose weight"
    }
  },
  methods: {
    async calculateAMR() {
      const userData = {
        gender: this.gender.toLowerCase(),
        weightInKg: this.weight,
        heightInCm: this.height,
        ageInYears: this.age,
        activityLevel: this.activity,
        fitnessGoal: this.fitnessGoal
      };

      try {
        const response = await services.submitUserForm(userData);
        console.log(response);
        this.$router.push('/food')
        //location.reload()
      } catch (error) {
        console.error(error);
      }
    }
  }
}
</script>

<style scoped>
.bg-image {
  background-image: url('image2.jpg');
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  filter: brightness(1.2) contrast(1.2);
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
