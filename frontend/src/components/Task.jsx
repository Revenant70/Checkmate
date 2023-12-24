import { useState, useEffect } from "react";
import axios from "axios";

export default function Task() {
  const [tasks, setTasks] = useState([]);

  const fetchUserTasks = async () => {
    try {
      const token = localStorage.getItem("JWT");
      const result = await axios.get("http://localhost:8080/api/tasks", {
        headers: {
          'Authorization': `Bearer ${token}`
        },
      })
      console.log(result)
      if(result.data.isEmpty) {
        console.log("No data");
      } else {
        setTasks(result.data);
      }
    } catch (e) {
      console.log(e)
    }
    
  };

  useEffect(() => {
    fetchUserTasks();
  }, []);

  return (
      <div className="overflow-x-auto cols-xs-1 text-center flex justify-center align-middle mt-12">
        <table className="table w-1/2">
          {/* head */}
          <thead>
            <tr>
              <th></th>
              <th></th>
              <th>Task</th>
              <th>Description</th>
              <th>Due date</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {tasks.length > 0
              ? tasks.map((task, index) => (
                    <tr key={index}>
                      <th className="flex items-center gap-2">
                        <div>{index + 1}</div>
                      </th>
                      <th>
                        <label>
                          <input type="checkbox" className="checkbox" />
                        </label>
                      </th>
                      <th>
                        <div className="flex items-center gap-3">
                          <div>{task.title}</div>
                        </div>
                      </th>
                      <th>{task.desc}</th>
                      <th>{task.dueDate}</th>
                      <th>
                        <button className="btn btn-ghost btn-s">details</button>
                      </th>
                    </tr>
                ))
              : null}
          </tbody>
          {/* foot */}
        </table>
      </div>
  );
}
