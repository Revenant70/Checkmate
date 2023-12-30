import { useState, useEffect } from "react";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faEllipsis,
  faCheck,
  faTrash,
  faPen,
  faX,
} from "@fortawesome/free-solid-svg-icons";
import { motion, AnimatePresence } from "framer-motion";

export default function Task() {
  const [taskId, setTaskId] = useState("");
  const [taskName, setTaskName] = useState("");
  const [desc, setDescName] = useState("");
  const [dueDate, setDueDate] = useState("");
  const [status, setStatus] = useState("");

  const [tasks, setTasks] = useState([]);

  const [isOpen, setIsOpen] = useState(false);

  const shakeVariants = {
    initial: { x: 0 },
    shake: {
      x: [-2.5, 2.5, -2.5, 2.5, 0],
      transition: { duration: 0.3 },
    },
  };

  const deleteTask = async (taskId) => {
    console.log(taskId);
    try {
      const token = localStorage.getItem("JWT");
      const response = await axios.delete(
        `http://localhost:8080/api/tasks/${taskId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.status == 200) {
        console.log("Task deleted");
      } 
    } catch (e) {
      console.log("Task failed to be added to the system:", e.message);
    }

  }

  const editTask = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("JWT");
      const response = await axios.put(
        `http://localhost:8080/api/tasks/${taskId}`,
        {
          title: taskName,
          desc: desc,
          dueDate: dueDate,
          status: status,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.status == 200) {
        setIsOpen(!isOpen);
        console.log("Task edited");
      } 
    } catch (e) {
      console.log("Task failed to be added to the system:", e.message);
    }
  };

  const fetchUserTasks = async () => {
    try {
      const token = localStorage.getItem("JWT");
      const result = await axios.get("http://localhost:8080/api/tasks", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log(result.data);
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

  function toggleModalFunction() {
    setIsOpen(!isOpen);
  }

  return (
    <div className="h-5/6 flex justify-center items-start">
      <table className="table w-1/2 overflow-x-auto cols-xs-1 text-center">
        {/* head */}
        <thead>
          <tr>
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
                  <th>
                    <div className="flex items-center justify-center gap-3">
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
                            onClick={() => deleteTask(task.taskid)}
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
      <AnimatePresence>
        {isOpen && (
          <motion.form
            onSubmit={editTask}
            className="card-body w-1/4 h-3/5 absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-primary-content rounded-lg flex justify-center align-middle"
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          >
            <div className="p-4">
              <motion.div
                className="fixed top-5 right-5"
                whileHover={{ scale: 1.085 }}
                whileTap={{ scale: 0.9 }}
              >
                <FontAwesomeIcon
                  className="cursor-pointer"
                  size="xl"
                  icon={faX}
                  onClick={toggleModalFunction}
                />{" "}
              </motion.div>
              <div className="form-control">
                <h2 className="text-2xl font-bold text-center pb-3">
                  Enter task details below
                </h2>
                <label className="label">
                  <span className="label-text">Task Name</span>
                </label>
                <input
                  type="text"
                  placeholder="task name"
                  onChange={(e) => setTaskName(e.target.value)}
                  className="input input-bordered"
                  required
                />
              </div>
              <div className="form-control">
                <label className="label">
                  <span className="label-text">Description</span>
                </label>
                <input
                  type="text"
                  placeholder="description"
                  onChange={(e) => setDescName(e.target.value)}
                  className="input input-bordered"
                />
              </div>
              <div className="form-control">
                <label className="label">
                  <span className="label-text">Due Date</span>
                </label>
                <input
                  type="text"
                  placeholder="due date"
                  onChange={(e) => setDueDate(e.target.value)}
                  className="input input-bordered"
                />
              </div>
              <div className="form-control">
                <label className="label">
                  <span className="label-text">Status</span>
                </label>
                <input
                  type="text"
                  placeholder="status"
                  onChange={(e) => setStatus(e.target.value)}
                  className="input input-bordered"
                />
              </div>
              <div className="form-control mt-6">
                <button className="btn btn-primary">Submit task</button>
              </div>
            </div>
          </motion.form>
        )}
      </AnimatePresence>
    </div>
  );
}
