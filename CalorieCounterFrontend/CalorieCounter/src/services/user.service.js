const baseUrl = 'http://localhost:8008';

const selectGender = async (gender) => {
  const endpoint = gender === 'male' ? '/maleUser' : '/femaleUser';
  const response = await fetch(baseUrl + endpoint);
  const data = await response.json();
  return data;
};

const submitUserForm = async (userData) => {
  const { gender, weightInKg, heightInCm, ageInYears, activityLevel, fitnessGoal} = userData;

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
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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

    const activityResponse = await fetch(baseUrl + activityEndpoint, { method: 'POST' });

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

    const fitnessGoalResponse = await fetch(baseUrl + fitnessGoalEndpoint, { method: 'POST' });

    if (fitnessGoalResponse.status !== 200) {
      throw new Error('Error getting fitness goal level');
    }

    return 'User data has been submitted successfully';
  } catch (error) {
    console.error(error);
    throw new Error('Internal server error');
  }
};

export default {
  selectGender,
  submitUserForm,
};
