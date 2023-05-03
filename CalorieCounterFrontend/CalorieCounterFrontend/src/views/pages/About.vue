<template>
  <v-app>
    <v-main class="bg-grey-darken-4">
      <div class="container overflow-auto">
        <div class="row">
          <div class="col-4">
            <nav id="navbar-example3" class="h-100 flex-column align-items-stretch pe-4 border-end">
              <nav class="nav nav-pills flex-column mt-9">
                <a class="btn btn-primary mt-9" href="#item-1">How Many Calories Do I Need Each Day?</a>
                <br>
                <nav class="nav nav-pills flex-column">
                  <a class="btn btn-primary ms-3 my-1" href="#item-1-1">Calculating Your Daily Calories</a>
                  <br>
                  <a class="btn btn-primary ms-3 my-1" href="#item-1-2">Step 1: Calculate Your BMR</a>
                  <br>
                </nav>
                <a class="btn btn-primary" href="#item-2">Step 2: Calculate Your AMR</a>
                <br>
                <a class="btn btn-primary" href="#item-3">How to Use This Information</a>
                <br>
                <nav class="nav nav-pills flex-column">
                  <a class="btn btn-primary ms-3 my-1" href="#item-3-1">Test Accuracy</a>
                </nav>
              </nav>
            </nav>
          </div>

          <div class="col-8">
            <div data-bs-spy="scroll" data-bs-target="#navbar-example3" data-bs-smooth-scroll="true"
              class="scrollspy-example-2" tabindex="0">
              <div id="item-1">
                <h4 id="header1">How Many Calories Do I Need Each Day?</h4>
                <p id="paragraph1">Knowing how many calories you need to consume each day is essential for losing,
                  gaining, or maintaining weight. One way to determine this with a technique called the Harris-Benedict
                  formula, which is one method used to estimate your basal metabolic rate (BMR).

                  By definition, BMR is your rate of metabolism (the conversion of calories and oxygen to energy) at rest.
                  It is the minimum level of energy required to sustain vital functions such as breathing, digestion, and
                  circulation. The Harris-Benedict formula is often used to assist weight loss by ensuring that you reduce
                  the intake of calories below what you need to maintain weight.</p>
              </div>
              <div id="item-1-1">
                <h5 id="header2">Calculating Your Daily Calories</h5>
                <p id="paragraph2">The Harris-Benedict formula is used to describe your basal metabolic rate (BMR) as a
                  numeric value. Your BMR is determined by your sex, age, and body size, and calculating this number tells
                  you how about how many calories you burn just being alive and awake.﻿﻿﻿

                  The formula for the BMR is quite complex.



                  Calculate your AMR by multiplying your BMR and by your current level of activity.</p>
              </div>
              <div id="item-1-2">
                <h5 id="header3">Step 1: Calculate Your BMR</h5>
                <p id="paragraph3">-For women, BMR = 655.1 + (9.563 x weight in kg) + (1.850 x height in cm) - (4.676 x
                  age in years)
                  <br>
                  -For men, BMR = 66.47 + (13.75 x weight in kg) + (5.003 x height in cm) - (6.755 x age in years)
                </p>
                <p id="paragraph4">Once you get out of bed and begin to move around, you will need to adjust this figure
                  as you expend more energy. This value, called active metabolic rate (AMR), is calculated by multiplying
                  your BMR by an assigned number representing the various activity levels. This number ranges from 1.2 for
                  being sedentary up to 1.9 for being very active.</p>
              </div>
              <div id="item-2">
                <h4 id="header4">Step 2: Calculate Your AMR</h4>
                <p id="paragraph5">-Sedentary (little or no exercise): AMR = BMR x 1.2
                  <br>
                  -Lightly active (exercise 1–3 days/week): AMR = BMR x 1.375
                  <br>
                  -Moderately active (exercise 3–5 days/week): AMR = BMR x 1.55
                  <br>
                  -Active (exercise 6–7 days/week): AMR = BMR x 1.725
                  <br>
                  -Very active (hard exercise 6–7 days/week): AMR = BMR x 1.9
                  <br>
                </p>
                <p id="paragraph6">Your AMR represents the number of calories you need to consume each day to stay at your
                  current weight. If you want to lose weight, you need to increase your level of physical activity or
                  decrease your caloric intake by eating less.</p>
              </div>
              <div id="item-3">
                <h4 id="header5">How to Use This Information</h4>
                <p id="paragraph7">For the purpose of weight loss, the AMR provides you the means to figure out how many
                  calories you either need to exclude and/or how many calories you need to burn through added exercise, to
                  lose a specific amount of weight.
                  <br><br>
                  For example, if your BMR is 1,400 (the average for American women) and you are moderately active, your
                  AMR would be 2,170 (1,400 x 1.55). Since a pound of fat is equal to 3,500 calories, you would need to
                  cut 500 calories per day to lose a pound per week. This is called your calorie deficit.
                  <br><br>
                  If you plan to lose weight simply by diet, your daily calorie intake would be 1,670 (2,170 - 500 =
                  1,670). If you plan to do it by exercise alone, you would need to burn 500 calories each day above and
                  beyond what you already do. For this reason, a combination of diet and exercise almost always achieves
                  the best result.
                </p>
              </div>
              <div id="item-3-1">
                <h5 id="header6">Test Accuracy</h5>
                <p id="paragraph8">Unfortunately, the Harris-Benedict formula isn't exactly perfect. According to the
                  Academy of Nutrition and Dietetics, research studies have indicated the formula is about 90% accurate
                  around 60% of the time.
                  <br><br>

                  That means it could be way off about 40% of the time, which is rather disheartening. Even worse, when
                  the formula was wrong, it overestimated the calorie needs of the research subjects, meaning that they
                  were burning fewer calories than they thought.
                  <br><br>
                  The problems may be due to physical or genetic factors that impede or enhance metabolism in some people.
                  Moreover, there's a good chance that many people overestimate how physically active they are.
                  <br><br>
                  Despite these shortcomings, the Harris-Benedict formula provides you with a relatively good picture of
                  your general caloric needs.1 If you don't achieve weight loss based on the calculations, simply adjust
                  your daily calorie count up or down or recalculate your AMR based on a lower activity level.
                </p>
                <p id="paragraph9">Never consume less than 1,200 calories per day without medical supervision. Doing so
                  can send your body into starvation, slowing metabolism and increasing the risk of gallstones, gout,
                  fatigue, irregular periods, and malnutrition.</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- <div class="row">
  <div class="col-4">
    <nav id="navbar-example3" class="h-100 flex-column align-items-stretch pe-4 border-end">
      <nav class="nav nav-pills flex-column">
        <a class="nav-link" href="#item-1">Item 1</a>
        <nav class="nav nav-pills flex-column">
          <a class="nav-link ms-3 my-1" href="#item-1-1">Item 1-1</a>
          <a class="nav-link ms-3 my-1" href="#item-1-2">Item 1-2</a>
        </nav>
        <a class="nav-link btn btn-primary" href="#item-2">Item 2</a>
        <a class="nav-link" href="#item-3">Item 3</a>
        <nav class="nav nav-pills flex-column">
          <a class="nav-link ms-3 my-1" href="#item-3-1">Item 3-1</a>
          <a class="nav-link ms-3 my-1" href="#item-3-2">Item 3-2</a>
        </nav>
      </nav>
    </nav>
  </div>

  <div class="col-8">
    <div data-bs-spy="scroll" data-bs-target="#navbar-example3" data-bs-smooth-scroll="true" class="scrollspy-example-2" tabindex="0">
      <div id="item-1">
        <h4>Item 1</h4>
        <p>...</p>
      </div>
      <div id="item-1-1">
        <h5>Item 1-1</h5>
        <p>...</p>
      </div>
      <div id="item-1-2">
        <h5>Item 1-2</h5>
        <p>...</p>
      </div>
      <div id="item-2">
        <h4>Item 2</h4>
        <p>...</p>
      </div>
      <div id="item-3">
        <h4>Item 3</h4>
        <p>...</p>
      </div>
      <div id="item-3-1">
        <h5>Item 3-1</h5>
        <p>...</p>
      </div>
      <div id="item-3-2">
        <h5>Item 3-2</h5>
        <p>...</p>
      </div>
    </div>
  </div>
</div> -->

    </v-main>
  </v-app>
</template>

<style scoped>
.container {
  height: 2300px;
  /* set a height for the container */
}

#header1 {
  position: absolute;
  top: 100px;
  font-size: xx-large;
  font-weight: bold;
}

#header2 {
  position: absolute;
  top: 350px;
  font-size: xx-large;
  font-weight: bold;

}

#header3 {
  position: absolute;
  top: 570px;
  font-size: x-large;
  font-weight: bold;
}

#header4 {
  position: absolute;
  top: 880px;
  font-size: x-large;
  font-weight: bold;
}

#header5 {
  position: absolute;
  top: 1250px;
  font-size: xx-large;
  font-weight: bold;
}

#header6 {
  position: absolute;
  top: 1640px;
  font-size: xx-large;
  font-weight: bold;
}

#paragraph1 {
  position: absolute;
  top: 180px;
  color: black;
  background-color: white;
}

#paragraph2 {
  position: absolute;
  top: 440px;
  color: black;
  background-color: white;
}

#paragraph3 {
  position: absolute;
  top: 640px;
  background-color: whitesmoke;
  color: red;
  border-style: solid;
  border-color: red;
}

#paragraph4 {
  position: absolute;
  top: 740px;
  background-color: white;
  color: black;
}

#paragraph5 {

  position: absolute;
  top: 940px;
  background-color: whitesmoke;
  color: red;
  border-style: solid;
  border-color: red;
}

#paragraph6 {
  position: absolute;
  top: 1140px;
  background-color: white;
  color: black;
}

#paragraph7 {
  position: absolute;
  top: 1340px;
  background-color: white;
  color: black;
}

#paragraph8 {
  position: absolute;
  top: 1740px;
  background-color: white;
  color: black;
}

#paragraph9 {
  position: absolute;
  top: 2130px;
  background-color: whitesmoke;
  color: red;
  border-style: solid;
  border-color: red;
}</style>
