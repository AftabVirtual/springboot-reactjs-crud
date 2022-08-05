import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import './Home.css';
import './Error.css';

const Home = () => {
    console.log('here home component');
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [users, setUsers] = useState([]);
    useEffect(() => {
        fetch("/api/users")
            .then(res => res.json())
            .then(
                (data) => {
                    setIsLoaded(true);
                    setUsers(data);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, [])
    if (error) {
        return <div><div className="Error"></div>
        <div className="overlay"></div>
        <div className="terminal">
          <h1>Error <span className="errorcode">The Backend Server is Not running</span></h1>
          <p className="output">Go to "springboot-reactjs-crud/e-wallet/reaMe.md" and follow the instructions to run the backend server.</p>
          <p className="output">Backend Server should be running at https://localhost:8080</p>
          <p className="output">Good luck.</p>
        </div></div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        return (
            <div className='userlist'>
                <h1>Users</h1>
                <ul>
                    {users.map(user => (
                        <li>
                            <Link to={`/user/${user._id}`}>{user.name}</Link>
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}

export default Home;