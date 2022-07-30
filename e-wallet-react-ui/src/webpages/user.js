import React, { useState, useEffect }  from 'react';
import { useParams } from "react-router-dom";
const User = props => {
    console.log("props"+props);
    var id = useParams().id;
    console.log('here user component', id);
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [isEdit, setEdit] = useState([]);
    const [user, setUser] = useState([]);
    const [topup, setTopup] = useState("");

    useEffect(() => {
        fetch("/api/users/" + id)
            .then(res => res.json())
            .then(
                (data) => {
                    console.log(data);
                    setIsLoaded(true);
                    setUser(data);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, [])
    if (error) {
        return <div>Error: {error.message}</div>;
    }
    if (!isLoaded) {
        return <div>Loading...</div>;
    }  
    const isEditBalance = (data,index)=>{
       user.wallets.map((e,i)=>{
           if(i==index){
               isEdit[i]=data;
            setEdit([...isEdit])
           }
       })
       console.log(isEdit);
    }

    const saveBalance = async (itm,index)=>{
            console.log("{itm._id}:"+itm._id);
            console.log("topup:"+ topup);

            const url = `/api/users/${id}/wallets/${itm._id}/topup/`;
            console.log("#### TOPUP URL: "+url);
               await fetch(url, {
                method: 'PUT',
                headers: {
                   'Content-type': 'application/json; charset=UTF-8',
                },
                body: JSON.stringify(topup)
                })
                .then((response) => response.json())
                .then((data) => {
                    // console.log("data:"+data);
                    setTopup(data);
                    user.wallets.map((e,i)=>{
                        if(i==index){
                            isEdit[i]=false;
                         setEdit([...isEdit])
                        }
                    })
                })
                .catch((err) => {
                   console.log(err.message);
                });
    }

    if (user) {
        return (
            <div className='card'>
                <h1>{user.name} Details</h1>
                {user.wallets.map((itm,idx)=>(
                    <div className='userDetails' key={idx}>
                    <div>Wallet name <span>{itm.walletName}</span></div>
                    <div>Balance<span>{ isEdit[idx]? <><input type="text" value={topup} onChange={(event) => setTopup(event.target.value)}/><button onClick={()=>saveBalance(itm,idx)}>Save</button> 
                    <button onClick={()=>isEditBalance(false,idx)}>Cancel</button></>:
                      <> {itm.Balance < topup?topup:itm.Balance} <button onClick={()=>isEditBalance(true,idx)} onChange={()=>isEditBalance(true,idx)}>Update Balance</button></>
                    }
                    </span></div>
                    </div>
                ))}
          </div>
        );
    }
}
export default User;