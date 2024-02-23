import React from 'react';

const Userprofile = () => {
  const user = localStorage.getItem('user');
  const email = localStorage.getItem('email');

  return (
    <div style={{ backgroundColor: '#f5f5f5', padding: '20px' }}>
      <h1 style={{ fontSize: '36px', marginBottom: '20px' }}>Profile</h1>
      <table style={{ fontSize: '18px' }}>
        <tbody>
          <tr>
            <td>Username : </td>
            <td>{user}</td>
          </tr>
          <tr>
            <td>Email : </td>
            <td>{email}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default Userprofile;
