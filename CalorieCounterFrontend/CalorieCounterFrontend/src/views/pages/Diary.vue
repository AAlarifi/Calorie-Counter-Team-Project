<template>
    <v-app>
        <v-main class="bg-grey-darken-4">
      <div class="bg-image">
      <v-container
        style="height: 70vh; max-height: 100vh"
        class="align-center d-flex">
        <v-row justify="center">
          <v-col cols="12" lg="6">
            <v-card elevation="10">
              <v-card-item>
                <v-card-title class="text-center">{{ foodName }}</v-card-title>
              </v-card-item>
              <v-card-text>
                <v-form @submit.prevent="submitData">
                  <v-text-field v-model="NOS" label="Number of Servings"></v-text-field>
                  <v-select label="Serving Size" :items="measurements" item-title="desc" item-value="level"
                    v-model="activity"></v-select>
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-btn @click="submitData" block variant="outlined btn btn-primary"
                  >Add food</v-btn
                >
              </v-card-actions>
              <v-card-item class="">
              <v-card-text class="text-center">Calories{{ foodInfomation }}</v-card-text>
            </v-card-item>
            </v-card>
              {{ response }}
          </v-col>
        </v-row>
      </v-container>
    </div>
    </v-main>
    </v-app>
</template>

<script>
import userServices from "../../services/user.service"
import foodServices from "../../services/food.service"

export default{
    data(){
        return {
        measurements: [
        { level: "small", desc: "small" },
        { level: "medium", desc: "medium" },
        { level: "large", desc: "large" },
        { level: "gram", desc: "1.0 gram" },
        { level: "ounce", desc: "1.0 ounce" },
        { level: "pound", desc: "1.0 pound" },
        { level: "kilogram", desc: "1.0 kilogram" },
        { level: "cup", desc: "cup" },
        { level: "serving", desc: "serving" },
      ],
      activity: "gram",
      NOS: "25",
      foodName: "Here is the Food name"
    }
},
methods: {

}
}
</script>
<style scoped>
.bg-image {
  background-image: url('src/images/image2.jpg');
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