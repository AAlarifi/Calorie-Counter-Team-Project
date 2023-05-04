const baseUrl = 'http://localhost:8008/secured';

const selectGender = async (gender) => {
  const endpoint = gender === 'male' ? '/maleUser' : '/femaleUser';
  const response = await fetch(baseUrl + endpoint);
  const data = await response.json();
  return data;
};

const submitUserForm = async (userData) => {
  const { gender, weightInKg, heightInCm, ageInYears, activityLevel, fitnessGoal } = userData;

  let session_token = localStorage.getItem("session_token");
  if (!session_token) {
    return Promise.reject(new Error("No session token found. Please login again."));
  }

  let bmrEndpoint;
  if (gender === 'male') {
    bmrEndpoint = '/maleUser';
  } else if (gender === 'female') {
    bmrEndpoint = '/femaleUser';
  } else {
    throw new Error('Invalid gender');
  }

  try {
    const bmrResponse = await fetch(baseUrl + bmrEndpoint, {
      method: 'POST',
      body: `weightInKg=${weightInKg}&heightInCm=${heightInCm}&ageInYears=${ageInYears}`,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        "X-Authorization": session_token
      },
    });

    if (bmrResponse.status !== 200) {
      throw new Error('Error calculating BMR');
    }

    let activityEndpoint;
    switch (activityLevel) {
      case 'sedentary':
        activityEndpoint = '/sedentary';
        break;
      case 'lightlyActive':
        activityEndpoint = '/lightlyActive';
        break;
      case 'moderatelyActive':
        activityEndpoint = '/moderatelyActive';
        break;
      case 'active':
        activityEndpoint = '/active';
        break;
      case 'veryActive':
        activityEndpoint = '/veryActive';
        break;
      default:
        throw new Error('Invalid activity level');
    }

    const activityResponse = await fetch(baseUrl + activityEndpoint, {
      method: 'POST',
      headers: { "X-Authorization": session_token }
    });

    if (activityResponse.status !== 200) {
      throw new Error('Error getting activity level');
    }

    let fitnessGoalEndpoint;
    if (fitnessGoal === 'Lose weight') {
      fitnessGoalEndpoint = '/loseWeight';
    } else if (fitnessGoal === 'Gain weight') {
      fitnessGoalEndpoint = '/gainWeight';
    } else {
      throw new Error('Invalid fitness goal');
    }

    const fitnessGoalResponse = await fetch(baseUrl + fitnessGoalEndpoint, {
      method: 'POST',
      headers: { "X-Authorization": session_token }
    });

    if (fitnessGoalResponse.status !== 200) {
      throw new Error('Error getting fitness goal level');
    }

    return 'User data has been submitted successfully';
  } catch (error) {
    console.error(error);
    throw new Error('Internal server error');
  }
};

const getCalorieIntake = async () => {
  let session_token = localStorage.getItem("session_token");
  if (!session_token) {
    return Promise.reject(new Error("No session token found. Please login again."));
  }
  try {
    const response = await fetch(baseUrl + '/getCalorieIntake', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        "X-Authorization": session_token
      }
    });

    if (response.status !== 200) {
      throw new Error('Error fetching the calorie intake');
    }
    const data = await response.json();
    return data;

  } catch (error) {
    console.error(error);
    //console.error(data);
    throw new Error('Internal server error')
  }
};

const createUser = (firstName, lastName, email, password) => {

  const requestBody = new URLSearchParams({
    firstName: firstName,
    lastName: lastName,
    email: email,
    password: password
  });

  return fetch("http://localhost:8008/signup", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body: requestBody
  })
    .then((response) => {
      if (response.status === 200) {
        return response.text();
      }
      if (response.status === 400) {
        return Promise.reject(new Error("Email already exist in the database."));
      }
    })
    .then((res) => {
      return res
    })
    .catch((error) => {
      console.log("err", error)
      return Promise.reject(error)
    })
};

const login = (email, password) => {
  const requestBody = new URLSearchParams({
    email: email,
    password: password
  });

  return fetch("http://localhost:8008/login",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: requestBody
    })
    .then((response) => {
      if (response.status === 200) {
        return response.text().then(text => {
          return JSON.parse(text);
        });
      } else if (response.status === 404) {
        throw "Bad request"
      } else {
        throw "Something went wrong"
      }
    })
    .then((resJson) => {
      localStorage.setItem("user_id", resJson.user_id);
      localStorage.setItem("session_token", resJson.session_token)
      return resJson
    })
    .catch((error) => {
      console.log("Err", error)
      return Promise.reject(error)
    })
}

// Logs out the user removing their session token and user id
const logout = () => {
  let session_token = localStorage.getItem("session_token");
  if (!session_token) {
    return Promise.reject(new Error("No session token found. Please login again."));
  }
  localStorage.removeItem("session_token");
  localStorage.removeItem("user_id");
  return fetch("http://localhost:8008/secured/logout", {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
      "X-Authorization": session_token
    }
  })
    .then((response) => {
      if (response.status === 200) {
        return Promise.resolve();
      } else {
        throw "Something went wrong"
      }
    })
    .catch((error) => {
      console.log("Err", error)
      return Promise.reject(error)
    });
};

export default {
  selectGender,
  submitUserForm,
  getCalorieIntake,
  createUser: createUser,
  login: login,
  logout: logout
};
