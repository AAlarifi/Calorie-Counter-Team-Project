const submitCalories = (food, calories) => {
  let session_token = localStorage.getItem("session_token");
  let user_id = localStorage.getItem("user_id");
  if (!user_id) {
    return Promise.reject(new Error("No user ID found. Please login again."));
  }
  if (!session_token) {
    return Promise.reject(new Error("No session token  found. Please login again."));
  }
    return fetch(`http://localhost:8008/secured/food?name=${food}&calories=${calories}&addedByUserId=${user_id}`, {
        method: "POST",
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            "X-Authorization": session_token
        }
    })
        .then((response) => {
            if (response.status === 200) {
                return response.text();
            }
            else {
                throw "Something went wrong"
            }
        })
        .then((res) => {
            return res
        })
        .catch((error) => {
            console.log("err", error)
            return Promise.reject(error)
        })
}

const foodCaloriesCalc = () => {
  let session_token = localStorage.getItem("session_token");
  if (!session_token) {
    return Promise.reject(new Error("No session token found. Please login again."));
  }
    return fetch(`http://localhost:8008/secured/foodCalories`, {
        method: "GET",
        headers: {
            "X-Authorization": session_token
        }
    })
        .then((response) => {
            if (response.status === 200) {
                return response.text();
            }
            else {
                throw "Something went wrong"
            }
        })
        .then((res) => {
            return res
        })
        .catch((error) => {
            console.log("err", error)
            return Promise.reject(error)
        })
}

const searchFood = (search) => {
  let session_token = localStorage.getItem("session_token");
  if (!session_token) {
    return Promise.reject(new Error("No session token found. Please login again."));
  }
    if (!search) {
        return Promise.reject("Search string is empty or undefined");
    }
    const encodedSearch = encodeURIComponent(search);

    return fetch(`http://localhost:8008/secured/food/search?query=${encodedSearch}`, {
        method: "GET",
        headers: {
            "X-Authorization": session_token
        }
    })
        .then((response) => {
            if (response.status === 200) {
                return response.text();
            }
            else {
                throw "Something went wrong"
            }
        })
        .then((res) => {
            return res
        })
        .catch((error) => {
            console.log("err", error)
            return Promise.reject(error)
        })
}

const searchParser = (result) => {
  let session_token = localStorage.getItem("session_token");
  if (!session_token) {
    return Promise.reject(new Error("No session token found. Please login again."));
  }
    if (!result) {
        return Promise.reject("result is empty or undefined");
    }
    const encodedSearch = encodeURIComponent(result);

    return fetch(`http://localhost:8008/secured/food/searchParser?query=${encodedSearch}`, {
        method: "GET",
        headers: {
            "X-Authorization": session_token
        }
    })
        .then((response) => {
            if (response.status === 200) {
                return response.text();
            }
            else {
                throw "Something went wrong"
            }
        })
        .then((res) => {
            return res
        })
        .catch((error) => {
            console.log("err", error)
            return Promise.reject(error)
        })
}

const foodInformation = (foodData) => {
    const { NOS, foodId, measurements } = foodData
    let session_token = localStorage.getItem("session_token");
    if (!session_token) {
      return Promise.reject(new Error("No session token found. Please login again."));
    }
    if (!NOS || !foodId || !measurements) {
        return Promise.reject("Number of servings, serving size, or food ID is empty or undefined");
    }

    let measurementsOption;
    switch (measurements) {
        case 'ml':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_milliliter';
            break;
        case 'gram':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_gram';
            break;
        case 'ounce':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_ounce';
            break;
        case 'pound':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_pound';
            break;
        case 'kilogram':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram';
            break;
        case 'cup':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_cup';
            break;
        case 'serving':
            measurementsOption = 'http://www.edamam.com/ontologies/edamam.owl#Measure_serving';
            break;
        default:
            return Promise.reject('Invalid measurement level');
    }

    const requestBody = new URLSearchParams({
        quantity: NOS,
        measureURI: measurementsOption,
        foodId: foodId
    });

    return fetch(`http://localhost:8008/secured/food/searchRequest`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "X-Authorization": session_token
        },
        body: requestBody
    })
        .then((response) => {
            if (response.status === 200) {
                return response.json()
            }
            else {
                throw "Something went wrong"
            }
        })
        .then((res) => {
            return res
        })
        .catch((error) => {
            console.log("err", error)
            return Promise.reject(error)
        })
}

// Exports functions
export default {
    submitCalories: submitCalories,
    foodCaloriesCalc: foodCaloriesCalc,
    searchFood: searchFood,
    searchParser: searchParser,
    foodInformation: foodInformation
}