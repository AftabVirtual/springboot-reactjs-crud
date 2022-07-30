import React, { useState, useEffect }  from 'react';
import { Link } from 'react-router-dom';
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
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        return (
            <div className='card'>
            <h1>Users List</h1>
            
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