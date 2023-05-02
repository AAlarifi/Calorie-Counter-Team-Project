<template>
    <div>
      <label for="gender">Select gender:</label>
      <select id="gender" v-model="gender">
        <option value="menBMR">Male</option>
        <option value="womenBMR">Female</option>
      </select>
      <br />
      <label for="weight">Weight (in kg):</label>
      <input id="weight" type="number" v-model="weight" />
      <br />
      <label for="height">Height (in cm):</label>
      <input id="height" type="number" v-model="height" />
      <br />
      <label for="age">Age (in years):</label>
      <input id="age" type="number" v-model="age" />
      <br />
      <label for="activityLevel">Select activity level:</label>
      <select id="activityLevel" v-model="activityLevel">
        <option value="sedentary">Sedentary (little or no exercie)</option>
        <option value="lightlyActive">Lightly active (exercise 1-3 days/week)</option>
        <option value="moderatelyActive">Moderately active (exercise 3-6 days/week)</option>
        <option value="Active">Active (exercise 6-7 days/week)</option>
        <option value="veryActive">Very active (hard exercise 6-7 days/week)</option>
      </select>
      <br />
      <button @click="calculateAMR">Calculate AMR</button>
      <br />
      <div v-if="AMR !== null">
        <p>AMR for {{gender === 'menBMR' ? 'men' : 'women'}} with {{weight}} kg, {{height}} cm, and {{age}} years old, and {{activityLevel}} activity level: {{AMR}}.</p>
      </div>
    </div>
  </template>
  
<script>
  export default {
    data() {
      return {
        gender: null,
        weight: null,
        height: null,
        age: null,
        activityLevel: null,
        AMR: null,
      };
    },
    methods: {
      calculateAMR() {
        if (this.weight && this.height && this.age) {
          if (this.gender === 'menBMR') {
            this.$http.post('/menBMR', { weight: this.weight, height: this.height, age: this.age })
              .then(response => {
                this.$http.post('/' + this.activityLevel, { BMR: response.data.BMR })
                  .then(response => {
                    this.AMR = response.data.AMR;
                  })
                  .catch(error => {
                    console.error(error);
                  });
              })
              .catch(error => {
                console.error(error);
              });
          } else if (this.gender === 'womenBMR') {
            this.$http.post('/womenBMR', { weight: this.weight, height: this.height, age: this.age })
              .then(response => {
                this.$http.post('/' + this.activityLevel, { BMR: response.data.BMR })
                  .then(response => {
                    this.AMR = response.data.AMR;
                  })
                  .catch(error => {
                    console.error(error);
                  });
              })
              .catch(error => {
                console.error(error);
              });
          }
        }
      },
    },
  };
</script>
  