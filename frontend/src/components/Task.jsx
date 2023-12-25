import { useState, useEffect } from "react";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faEllipsis,
  faCheck,
  faTrash,
  faPen,
} from "@fortawesome/free-solid-svg-icons";
import { motion } from "framer-motion";

export default function Task() {
  const [tasks, setTasks] = useState([]);

  const shakeVariants = {
    initial: { x: 0 },
    shake: {
      x: [-2.5, 2.5, -2.5, 2.5, 0],
      transition: { duration: 0.3 },
    },
  };

  const fetchUserTasks = async () => {
    try {
      const token = localStorage.getItem("JWT");
      const result = await axios.get("http://localhost:8080/api/tasks", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log(result);
      if (result.data.isEmpty) {
        console.log("No data");
      } else {
        setTasks(result.data);
      }
    } catch (e) {
      console.log(e);
    }
  };

  useEffect(() => {
    fetchUserTasks();
  }, []);

  return (
    <div className="h-5/6 flex justify-center items-start">
      <table className="table w-1/2 overflow-x-auto cols-xs-1 text-center">
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
                    <div className="dropdown dropdown-right">
                      <div tabIndex={0} role="button" className="btn m-1">
                        <FontAwesomeIcon icon={faEllipsis} />
                      </div>
                      <motion.ul
                        tabIndex={0}
                        className="dropdown-content z-[1] menu flex flex-row justify-center items-center p-2 shadow bg-base-100 rounded-box w-52"
                      >
                        <li>
                          <motion.a
                            whileHover="shake"
                            initial="initial"
                            variants={shakeVariants}
                          >
                            <FontAwesomeIcon
                              color="green"
                              size="lg"
                              icon={faCheck}
                            />
                          </motion.a>
                        </li>
                        <li>
                          <motion.a
                            whileHover="shake"
                            initial="initial"
                            variants={shakeVariants}
                          >
                            <FontAwesomeIcon size="lg" icon={faPen} />
                          </motion.a>
                        </li>
                        <li>
                          <motion.a
                            whileHover="shake"
                            initial="initial"
                            variants={shakeVariants}
                          >
                            <FontAwesomeIcon
                              color="red"
                              size="lg"
                              icon={faTrash}
                            />
                          </motion.a>
                        </li>
                      </motion.ul>
                    </div>
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
