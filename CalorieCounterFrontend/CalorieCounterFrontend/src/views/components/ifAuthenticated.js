function ifAuthenticatedFunction(to, from, next) {
    // Check if the user has a token or a session in the local storage
    const loggedIn = localStorage.getItem('session_token');
    if (loggedIn) {
      // If the user is authenticated, allow them to access the protected route
      next()
      return
    } 
      // If the user is not authenticated, redirect them to the login page
      next('/login');
    
  }

  export const ifAuthenticated = {
    call: ifAuthenticatedFunction
}